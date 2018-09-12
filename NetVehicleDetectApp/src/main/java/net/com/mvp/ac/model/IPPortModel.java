package net.com.mvp.ac.model;

/**
 * 设置IP的model
 */

public class IPPortModel {
    String ip,ip_port;//接口的ip
    String file_ip,file_ip_port;//文件的ip

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp_port() {
        return ip_port;
    }

    public void setIp_port(String ip_port) {
        this.ip_port = ip_port;
    }

    public String getFile_ip() {
        return file_ip;
    }

    public void setFile_ip(String file_ip) {
        this.file_ip = file_ip;
    }

    public String getFile_ip_port() {
        return file_ip_port;
    }

    public void setFile_ip_port(String file_ip_port) {
        this.file_ip_port = file_ip_port;
    }

    @Override
    public String toString() {
        return "IPPortModel{" +
                "ip='" + ip + '\'' +
                ", ip_port='" + ip_port + '\'' +
                ", file_ip='" + file_ip + '\'' +
                ", file_ip_port='" + file_ip_port + '\'' +
                '}';
    }
}
