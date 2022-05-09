package vn.techmaster.job_hunt.model;

public enum City {
  HaNoi("Hà Nội"),
  HoChiMinh("Hồ Chí Minh"),
  HaiPhong("Hải Phòng"),
  DaNang("Đà Nẵng");

  public final String label;
  private City(String label) {
    this.label = label;
  }
}
