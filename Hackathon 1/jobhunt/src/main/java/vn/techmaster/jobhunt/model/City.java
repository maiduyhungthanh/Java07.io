package vn.techmaster.jobhunt.model;

public enum City {
    HaNoi("Hà Nội"),
    HoChiMinh("Hồ Chí Minh"),
    HaiPhong("Hải phòng"),
    DaNang("Đà Nẵng");
  
    public final String label;
    private City(String label) {
      this.label = label;
    }
  }
