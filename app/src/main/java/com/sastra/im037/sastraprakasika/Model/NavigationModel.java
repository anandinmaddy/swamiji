package com.sastra.im037.sastraprakasika.Model;

import java.util.ArrayList;

public class NavigationModel {

    private String mainCategory;
    private ArrayList<SubCategory> subCategories;

    public NavigationModel(String mainCategory, ArrayList<SubCategory> subCategories) {
        this.mainCategory = mainCategory;
        this.subCategories = subCategories;
    }

    public void setSubCategories(ArrayList<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public ArrayList<SubCategory> getSubCategories() {
        return subCategories;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public static class SubCategory {
        private String subCategoryName;

        public SubCategory(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }
    }
}
