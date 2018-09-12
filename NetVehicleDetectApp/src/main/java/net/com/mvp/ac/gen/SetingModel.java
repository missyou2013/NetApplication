package net.com.mvp.ac.gen;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018-07-09.
 */

@Entity
public class SetingModel   {
    @Id
    private long id;

    private String name;
    private String district_code;
    private String interface_addre;
    private String interface_number;
    private String organization_code;
    private String zhongduan_biaoshi;
    private String other;
    private String other_;
    @Generated(hash = 1545462039)
    public SetingModel(long id, String name, String district_code,
            String interface_addre, String interface_number,
            String organization_code, String zhongduan_biaoshi, String other,
            String other_) {
        this.id = id;
        this.name = name;
        this.district_code = district_code;
        this.interface_addre = interface_addre;
        this.interface_number = interface_number;
        this.organization_code = organization_code;
        this.zhongduan_biaoshi = zhongduan_biaoshi;
        this.other = other;
        this.other_ = other_;
    }
    @Generated(hash = 109804990)
    public SetingModel() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDistrict_code() {
        return this.district_code;
    }
    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }
    public String getInterface_addre() {
        return this.interface_addre;
    }
    public void setInterface_addre(String interface_addre) {
        this.interface_addre = interface_addre;
    }
    public String getInterface_number() {
        return this.interface_number;
    }
    public void setInterface_number(String interface_number) {
        this.interface_number = interface_number;
    }
    public String getOrganization_code() {
        return this.organization_code;
    }
    public void setOrganization_code(String organization_code) {
        this.organization_code = organization_code;
    }
    public String getZhongduan_biaoshi() {
        return this.zhongduan_biaoshi;
    }
    public void setZhongduan_biaoshi(String zhongduan_biaoshi) {
        this.zhongduan_biaoshi = zhongduan_biaoshi;
    }
    public String getOther() {
        return this.other;
    }
    public void setOther(String other) {
        this.other = other;
    }
    public String getOther_() {
        return this.other_;
    }
    public void setOther_(String other_) {
        this.other_ = other_;
    }
    

}
