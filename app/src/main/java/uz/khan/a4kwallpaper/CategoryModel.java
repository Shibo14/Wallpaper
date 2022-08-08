package uz.khan.a4kwallpaper;

public class CategoryModel {

 private    String categoryImg;
  private   String categoryImgUrl;

    public CategoryModel(String categoryImg, String categoryImgUrl) {
        this.categoryImg = categoryImg;
        this.categoryImgUrl = categoryImgUrl;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public String getCategoryImgUrl() {
        return categoryImgUrl;
    }

    public void setCategoryImgUrl(String categoryImgUrl) {
        this.categoryImgUrl = categoryImgUrl;
    }
}
