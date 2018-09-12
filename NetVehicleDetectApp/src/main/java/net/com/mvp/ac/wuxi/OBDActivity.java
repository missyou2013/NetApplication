package net.com.mvp.ac.wuxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;

import net.com.mvp.ac.R;
import net.com.mvp.ac.activity.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * OBD链接测试
 **/
public class OBDActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_left_txt)
    Button titleBtnLeftTxt;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.iv_headtip)
    ImageView ivHeadtip;
    @BindView(R.id.title_btn_right_txt)
    Button titleBtnRightTxt;
    @BindView(R.id.title_btn_right)
    ImageButton titleBtnRight;
    @BindView(R.id.ac_obd_content)
    TextView acObdContent;
    @BindView(R.id.ac_obd_btn)
    Button acObdBtn;
    @BindView(R.id.ac_obd_bluetooth_state)
    TextView acObdBluetoothState;
    @BindView(R.id.ac_obd_lv)
    ListView acObdLv;
    @BindView(R.id.ac_obd_editText)
    EditText acObdEditText;
    @BindView(R.id.ac_obd_btn2)
    Button acObdBtn2;

    private BluetoothAdapter defaultAdapter;
    private final static int REQUEST_CONNECT_DEVICE = 1;    //宏定义查询设备句柄

    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号
    private final static String OBDII_UUID = "00000000-0000-1000-8000-00805f9b34fb";
    String XIAOMI_UUID = "e3ba273c-d059-feaa-257f-084bc84a76db";
    private InputStream is;    //输入流，用来接收蓝牙数据
    private String smsg = "";    //显示用数据缓存
    private String fmsg = "";    //保存用数据缓存

    BluetoothDevice _device = null;     //蓝牙设备
    BluetoothSocket _socket = null;      //蓝牙通信socket
    public String filename = ""; //用来保存存储的文件名

    boolean _discoveryFinished = false;
    boolean bRun = true;
    boolean bThread = false;
    String MAC_address = "";

    //    设备名称小米手机
//    设备地址FC:64:BA:51:6F:F6==
//    设备名称OBDII
//    设备地址34:81:F4:CA:BE:85
//     ===!!!!!!===OBDII===00000000-0000-1000-8000-00805f9b34fb
// ===!!!!!!===小米手机===e3ba273c-d059-feaa-257f-084bc84a76db
//===!!!!!!===OPPO R11===00000000-0000-1000-8000-00805f9b34fb
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obd);
        ButterKnife.bind(this);
        if (BleManager.getInstance().isSupportBle()) {
            if (BleManager.getInstance().isBlueEnable()) {
                BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
//                        .setServiceUuids(serviceUuids)      // 只扫描指定的服务的设备，可选
//                        .setDeviceName(true, names)         // 只扫描指定广播名的设备，可选
//                        .setDeviceMac("FC:64:BA:51:6F:F6")                  // 只扫描指定mac的设备，可选
//                        .setAutoConnect(isAutoConnect)      // 连接时的autoConnect参数，可选，默认false
                        .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
                        .build();
                BleManager.getInstance().initScanRule(scanRuleConfig);

            } else {
                Toast.makeText(this, "开启蓝牙", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 0x01);
            }
        } else {
            acObdBluetoothState.setText("当前蓝牙连接状态：无法连接");
        }

        acObdLv.setOnItemClickListener(this);
        // 注册BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // 不要忘了之后解除绑定

        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        chackBluetooth();
    }

    @OnClick({R.id.ac_obd_content, R.id.ac_obd_btn, R.id.ac_obd_btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_obd_btn2:
                onSendButtonClicked();
                break;
            case R.id.ac_obd_content:
                break;
            case R.id.ac_obd_btn:
                getBondBluetooth();

                break;
        }
    }

    private void chackBluetooth() {
        defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
//            show_tv.setText("本机不支持蓝牙功能");
            Toast.makeText(this, "本机不支持蓝牙功能", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!defaultAdapter.isEnabled()) {
//            show_tv.setText("有蓝牙功能，还没打开");
            Toast.makeText(this, "有蓝牙功能，还没打开", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("提示");
            builder.setMessage("蓝牙设备没打开，是否打开");
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 100);
                }
            });
            builder.show();

        } else {
            Toast.makeText(this, "蓝牙已开启", Toast.LENGTH_SHORT).show();
//            show_tv.setText("蓝牙已开启");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:     //连接结果，由DeviceListActivity设置返回
                if (resultCode == Activity.RESULT_OK) {

                    Toast.makeText(this, "蓝牙已开启", Toast.LENGTH_SHORT).show();
//            show_tv.setText("蓝牙已开启");
                    // MAC地址，由DeviceListActivity设置返回
//            String address = data.getExtras()
//                    .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // 得到蓝牙设备句柄
                    _device = defaultAdapter.getRemoteDevice(MAC_address);

                    // 用服务号得到socket
                    try {
                        _socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                    } catch (IOException e) {
                        Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                    }
                    //连接socket
//            Button btn = (Button) findViewById(R.id.Button03);
                    try {
                        _socket.connect();
                        Toast.makeText(this, "连接" + _device.getName() + "成功！", Toast.LENGTH_SHORT).show();
//                btn.setText("断开");
                    } catch (IOException e) {

                        try {
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                            _socket.close();
                            _socket = null;
                        } catch (IOException ee) {
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }

                    //打开接收线程
                    try {
                        is = _socket.getInputStream();   //得到蓝牙数据输入流
                    } catch (IOException e) {
                        Toast.makeText(this, "接收数据失败！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (bThread == false) {
                        ReadThread.start();
                        bThread = true;
                    } else {
                        bRun = true;
                    }
                }
                break;
            default:
                break;
        }

    }

    BluetoothDevice bluetoothDevice;
    private OutputStream os;
    private void getBondBluetooth() {
        Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();

//        MAC_address=bondedDevices.
        List<String> list = new ArrayList<>();
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map = null;

        for (BluetoothDevice bond : bondedDevices) {
            map = new HashMap<>();
            map.put("name", bond.getName());
            map.put("address", bond.getAddress());
            ParcelUuid[] parcelUuids = bond.getUuids();
            ArrayList<String> uuidStrings = new ArrayList<>(parcelUuids.length);
            for (ParcelUuid parcelUuid : parcelUuids) {
                map.put("uuid", parcelUuid.getUuid().toString());
                if(OBDII_UUID.equals(parcelUuid.getUuid().toString())){
                    bluetoothDevice=bond;
                }
                uuidStrings.add(parcelUuid.getUuid().toString());
            }
            mapList.add(map);
            String msg = "设备名称" + bond.getName() + "\n设备地址" + bond.getAddress();
            Log.e("tag", msg + "==");
            list.add(msg);
        }
        if (mapList != null && mapList.size() > 0) {
            MAC_address = mapList.get(0).get("address");
        }
        for (int i = 0; i < mapList.size(); i++) {
            Log.e("tag", "===!!!!!!===" + mapList.get(i).get("name") + "===" + mapList.get(i).get("uuid"));
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        acObdLv.setAdapter(adapter);


                //通过BroadcastReceiver获取了BLuetoothDevice
                try {
                    UUID uuid = UUID.fromString(OBDII_UUID);
                    clienSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
                    clienSocket.connect();
                    InputStream inputStream = clienSocket.getInputStream();


                    // 获取到输出流，向外写数据
                    os = clienSocket.getOutputStream();
                    // 判断是否拿到输出流
                    if (os != null) {
                        // 需要发送的信息
                        String text = "成功发送信息";
                        // 以utf-8的格式发送出去
                        os.write(text.getBytes("UTF-8"));
                    }

                    String str = bytesToHexString(InputStreamToBytes(inputStream));
                    Log.e("tag", "接收到的蓝牙数据--------"+str);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

    }

    //接收数据线程
    Thread ReadThread = new Thread() {

        public void run() {
            int num = 0;
            byte[] buffer = new byte[1024];
            byte[] buffer_new = new byte[1024];
            int i = 0;
            int n = 0;
            bRun = true;
            //接收线程
            while (true) {
                try {
                    while (true) {
                        num = is.read(buffer);         //读入数据
                        //buffer中读取了返回蓝牙设备发送的消息 例如蓝牙设备按键识别了某个标签数据
                        n = 0;
                        String s0 = new String(buffer, 0, num);
                        fmsg = s0 + "\n";    //保存收到数据
                        Log.e("tag", "=============fmsg=====" + fmsg);
                        smsg += s0 + "\n";   //写入接收缓存
                        Log.e("tag", "=============smsg=====" + smsg);
                        if (is.available() == 0) break;  //短时间没有数据才跳出进行显示
                    }
                    //发送显示消息，进行显示刷新
                    handler.sendMessage(handler.obtainMessage());
                } catch (IOException e) {
                } catch (StringIndexOutOfBoundsException ex) {
                }
            }
        }
    };

    //消息处理队列
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            acObdContent.setText(smsg);   //显示数据
//            sv.scrollTo(0, dis.getMeasuredHeight()); //跳至数据最后一页
        }
    };

    //关闭程序掉用处理部分
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        if (_socket != null)  //关闭连接socket
            try {
                _socket.close();
            } catch (IOException e) {
            }
        // _bluetooth.disable();  //关闭蓝牙服务
    }

    //发送按键响应
    public void onSendButtonClicked() {
        int i = 0;
        int n = 0;
        try {
            OutputStream os = _socket.getOutputStream();   //蓝牙连接输出流
            byte[] bos = acObdEditText.getText().toString().getBytes();
            for (i = 0; i < bos.length; i++) {
                if (bos[i] == 0x0a) n++;
            }
            byte[] bos_new = new byte[bos.length + n];
            n = 0;
            for (i = 0; i < bos.length; i++) { //手机中换行为0a,将其改为0d 0a后再发送
                if (bos[i] == 0x0a) {
                    bos_new[n] = 0x0d;
                    n++;
                    bos_new[n] = 0x0a;
                } else {
                    bos_new[n] = bos[i];
                }
                n++;
            }

            os.write(bos_new);
        } catch (IOException e) {
        }
    }

    // 创建一个接收ACTION_FOUND广播的BroadcastReceiver
    BluetoothDevice device;
    BluetoothSocket clienSocket;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 发现设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从Intent中获取设备对象
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 将设备名称和地址放入array adapter，以便在ListView中显示
                Log.e("tag", "=======" + device.getName() + "/n" + device.getAddress());
//                mArrayAdapter.add(device.getName() + "/n" + device.getAddress());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        /** 耗时操作 */
                        //通过BroadcastReceiver获取了BLuetoothDevice
                        try {
                            UUID uuid = UUID.fromString(XIAOMI_UUID);
                            clienSocket = device.createRfcommSocketToServiceRecord(uuid);
                            clienSocket.connect();
                            InputStream inputStream = clienSocket.getInputStream();
                            String str = bytesToHexString(InputStreamToBytes(inputStream));
                            Log.e("tag", "接收到的蓝牙数据--------"+str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                /** 更新UI */
                            }
                        });
                    }
                }).start();


            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.e("tag", "find over-----------------------");
//                if (mNewDevicesAdapter.getCount() == 0) {
//                }
            }
        }
    };

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String InputStreamToString(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] data = new byte[bufferSize];
        int count = -1;
        while ((count = inputStream.read(data, 0, bufferSize)) != -1) {
            outputStream.write(data, 0, count);
            data = null;
        }
        return new String(outputStream.toByteArray(), "UTF-8");
    }

    public static byte[] InputStreamToBytes(InputStream inputStream) throws Exception {
        int bufferSize = 1024;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[bufferSize];
        int count = -1;
        while ((count = inputStream.read(data, 0, bufferSize)) != -1) {
            outputStream.write(data, 0, count);
            data = null;
        }

        return outputStream.toByteArray();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            InputStream inputStream = clienSocket.getInputStream();
            String str = bytesToHexString(InputStreamToBytes(inputStream));
            Log.e("tag", "接收到的蓝牙数据--------"+str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
