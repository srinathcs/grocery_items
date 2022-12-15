package com.example.testone;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    public static int ID = 0;
    public static int ORDER_ID = 0;
    public static final String ALL_ITEMS_KEY = "all_items";
    public static final String DB_NAME = "fake_database";
    public static final String CART_ITEM_KEY = "cart_item";
    public static Gson gson = new Gson();
    public static Type groceryListType = new TypeToken<ArrayList<GroceryItem>>() {
    }.getType();

    public static void initSharedPreference(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        if (null == allItems) {
            initAllItem(mContext);
        }

    }

    public static void clearSharedPreference(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    private static void initAllItem(Context mContext) {
        ArrayList<GroceryItem> allItem = new ArrayList<>();
        GroceryItem milk = new GroceryItem("Milk", "Milk Is Good For Health", "https://www.pngmart.com/files/5/Milk-PNG-Clipart-139x279.png"
                , "Drink", 2.3, 8);
        allItem.add(milk);

        GroceryItem iceCream = new GroceryItem("Ice Cream", "Delicious",
                "https://freepngimg.com/thumb/ice%20cream/15-ice-cream-png-image.png", "Food", 5.4, 10);
        allItem.add(iceCream);

        GroceryItem soda = new GroceryItem("Soda", "Tasty", "https://www.pngmart.com/files/16/Cocktail-Soda-Transparent-PNG.png",
                "Drink", 0.99, 15);
        allItem.add(soda);

        GroceryItem pizza = new GroceryItem("Pizza", "Fresh Homemade Italian Pizza Margherita with buffalo mozzarella and basil",
                "https://www.shutterstock.com/image-photo/fresh-homemade-italian-pizza-margherita-600w-1829205563.jpg", "Food", 10, 20);
        allItem.add(pizza);

        GroceryItem soap = new GroceryItem("Soap", "Soap bar and foam on white background, top view. Mockup for design",
                "https://www.shutterstock.com/image-photo/soap-bar-foam-on-white-600w-1416090086.jpg", "Cleanser", 3, 12);
        allItem.add(soap);

        GroceryItem juice = new GroceryItem("Juice", "Juice is soo good", "https://freepngimg.com/thumb/juice/4-2-juice-png-clipart.png",
                "Drink", 2, 10);
        allItem.add(juice);

        GroceryItem walnut = new GroceryItem("Walnut", "Newly arrived", "https://www.pngall.com/wp-content/uploads/2016/06/Walnut-Free-PNG-Image.png",
                "Nuts", 4, 30);
        allItem.add(walnut);

        GroceryItem almond = new GroceryItem("almond", "It's good for health", "https://www.shutterstock.com/image-photo/pile-almond-nuts-slice-green-600w-1935664681.jpg",
                "Nuts", 6, 20);
        allItem.add(almond);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY, gson.toJson(allItem));
        editor.commit();

    }

    public static void changeRate(Context mContext, int itemId, int newRate) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItem = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        if (null != allItem) {
            ArrayList<GroceryItem> newItem = new ArrayList<>();
            for (GroceryItem i : allItem) {
                if (i.getId() == itemId) {
                    i.setRate(newRate);
                    newItem.add(i);
                } else {
                    newItem.add(i);
                }
            }
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItem));
            editor.commit();
        }
    }

    public static ArrayList<GroceryItem> getAllItems(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        return allItems;
    }

    public static void addReview(Context mContext, Review review) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItem = getAllItems(mContext);
        if (null != allItem) {
            ArrayList<GroceryItem> newItem = new ArrayList<>();
            for (GroceryItem i : allItem) {
                if (i.getId() == review.getGroceryItemId()) {
                    ArrayList<Review> reviews = i.getReviews();
                    reviews.add(review);
                    i.setReviews(reviews);
                    newItem.add(i);
                } else {
                    newItem.add(i);
                }
            }
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItem));
            editor.commit();
        }
    }

    public static ArrayList<Review> getReviewById(Context mContext, int itemId) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItem = getAllItems(mContext);
        if (null != allItem) {
            for (GroceryItem i : allItem) {
                if (i.getId() == itemId) {
                    ArrayList<Review> reviews = i.getReviews();
                    return reviews;
                }
            }
        }
        return null;
    }

    public static void addItemToCard(Context mContext, GroceryItem item) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> cartItem = gson.fromJson(sharedPreferences.getString(CART_ITEM_KEY, null), groceryListType);
        if (cartItem == null) {
            cartItem = new ArrayList<>();
        }
        cartItem.add(item);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEM_KEY);
        editor.putString(CART_ITEM_KEY, gson.toJson(cartItem));
        editor.commit();
    }

    public static ArrayList<GroceryItem> getCartItem(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> cartItem = gson.fromJson(sharedPreferences.getString(CART_ITEM_KEY, null), groceryListType);
        return cartItem;
    }

    public static ArrayList<GroceryItem> searchForItems(Context mContext, String text) {
        ArrayList<GroceryItem> allItems = getAllItems(mContext);
        if (null != allItems) {
            ArrayList<GroceryItem> items = new ArrayList<>();
            for (GroceryItem item : allItems) {
                if (item.getName().equalsIgnoreCase(text)) {
                    items.add(item);
                }
                String[] names = item.getName().split(" ");
                for (int i = 0; i < names.length; i++) {
                    if (text.equalsIgnoreCase(names[i])) {
                        boolean doseExited = false;

                        for (GroceryItem j : items) {
                            if (j.getId() == item.getId()) {
                                doseExited = true;
                            }
                        }
                        if (!doseExited) {
                            items.add(item);
                        }
                    }
                }
            }
            return items;
        }
        return null;
    }

    public static ArrayList<String> getCategories(Context mContext) {
        ArrayList<GroceryItem> allItems = getAllItems(mContext);
        if (null != allItems) {
            ArrayList<String> categories = new ArrayList<>();
            for (GroceryItem item : allItems) {
                boolean doesExist = false;
                for (String s : categories) {
                    if (item.getCategory().equals(s)) {
                        doesExist = true;
                    }
                }
                if (!doesExist) {
                    categories.add(item.getCategory());
                }
            }
            return categories;
        }
        return null;
    }

    public static ArrayList<GroceryItem> getItemByCategory(Context mContext, String category) {
        ArrayList<GroceryItem> allItem = getAllItems(mContext);
        if (null != allItem) {
            ArrayList<GroceryItem> items = new ArrayList<>();
            for (GroceryItem item : allItem) {
                if (item.getCategory().equals(category)) {
                    items.add(item);
                }
            }
            return items;
        }
        return null;
    }

    public static void deleteItemFormCart(Context mContext, GroceryItem item) {
        ArrayList<GroceryItem> cartItem = getCartItem(mContext);
        if (null != cartItem) {
            ArrayList<GroceryItem> newItem = new ArrayList<>();
            for (GroceryItem i : cartItem) {
                if (i.getId() != item.getId()) {
                    newItem.add(i);
                }
            }
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(CART_ITEM_KEY);
            editor.putString(CART_ITEM_KEY, gson.toJson(newItem));
            editor.commit();
        }
    }

    public static void clearCartItems(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEM_KEY);
        editor.commit();
    }

    public static void increasePopularityPoint(Context mContext, GroceryItem item, int points) {
        ArrayList<GroceryItem> allItems = getAllItems(mContext);
        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : allItems) {
                if (i.getId() == item.getId()) {
                    i.setPopularPoint(i.getPopularPoint() + points);
                    newItems.add(i);
                } else {
                    newItems.add(i);
                }
            }
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            Gson gson = new Gson();
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }

    public static void changeUserPoint (Context mContext ,GroceryItem item ,int points){
        ArrayList<GroceryItem> allItems = getAllItems(mContext);
        if (null != allItems){
            ArrayList<GroceryItem> newItem = new ArrayList<>();
            for (GroceryItem i: allItems){
                if (i.getId()== item.getId()){
                    i.setUserPoint(i.getUserPoint()+points);
                }
                newItem.add(i);
            }
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItem));
            editor.commit();
        }
    }

    public static int getOrderId() {
        ORDER_ID++;
        return ORDER_ID;
    }

    public static int getID() {
        ID++;
        return ID;
    }

    public static String getLicenses(){
        String licenses ="";
        licenses += "Gson\n";
        licenses +="Definitions.\n" +
                "\n" +
                "      \"License\" shall mean the terms and conditions for use, reproduction,\n" +
                "      and distribution as defined by Sections 1 through 9 of this document.\n" +
                "\n" +
                "      \"Licensor\" shall mean the copyright owner or entity authorized by\n" +
                "      the copyright owner that is granting the License.\n" +
                "\n" +
                "      \"Legal Entity\" shall mean the union of the acting entity and all\n" +
                "      other entities that control, are controlled by, or are under common\n" +
                "      control with that entity. For the purposes of this definition,\n" +
                "      \"control\" means (i) the power, direct or indirect, to cause the\n" +
                "      direction or management of such entity, whether by contract or\n" +
                "      otherwise, or (ii) ownership of fifty percent (50%) or more of the\n" +
                "      outstanding shares, or (iii) beneficial ownership of such entity.\n" +
                "\n" +
                "      \"You\" (or \"Your\") shall mean an individual or Legal Entity\n" +
                "      exercising permissions granted by this License.\n" +
                "\n" +
                "      \"Source\" form shall mean the preferred form for making modifications,\n" +
                "      including but not limited to software source code, documentation\n" +
                "      source, and configuration files.\n" +
                "\n" +
                "      \"Object\" form shall mean any form resulting from mechanical\n" +
                "      transformation or translation of a Source form, including but\n" +
                "      not limited to compiled object code, generated documentation,\n" +
                "      and conversions to other media types.\n" +
                "\n" +
                "      \"Work\" shall mean the work of authorship, whether in Source or\n" +
                "      Object form, made available under the License, as indicated by a\n" +
                "      copyright notice that is included in or attached to the work\n" +
                "      (an example is provided in the Appendix below).\n" +
                "\n" +
                "      \"Derivative Works\" shall mean any work, whether in Source or Object\n" +
                "      form, that is based on (or derived from) the Work and for which the\n" +
                "      editorial revisions, annotations, elaborations, or other modifications\n" +
                "      represent, as a whole, an original work of authorship. For the purposes\n" +
                "      of this License, Derivative Works shall not include works that remain\n" +
                "      separable from, or merely link (or bind by name) to the interfaces of,\n" +
                "      the Work and Derivative Works thereof.\n" +
                "\n" +
                "      \"Contribution\" shall mean any work of authorship, including\n" +
                "      the original version of the Work and any modifications or additions\n" +
                "      to that Work or Derivative Works thereof, that is intentionally\n" +
                "      submitted to Licensor for inclusion in the Work by the copyright owner\n" +
                "      or by an individual or Legal Entity authorized to submit on behalf of\n" +
                "      the copyright owner. For the purposes of this definition, \"submitted\"\n" +
                "      means any form of electronic, verbal, or written communication sent\n" +
                "      to the Licensor or its representatives, including but not limited to\n" +
                "      communication on electronic mailing lists, source code control systems,\n" +
                "      and issue tracking systems that are managed by, or on behalf of, the\n" +
                "      Licensor for the purpose of discussing and improving the Work, but\n" +
                "      excluding communication that is conspicuously marked or otherwise\n" +
                "      designated in writing by the copyright owner as \"Not a Contribution.\"\n" +
                "\n" +
                "      \"Contributor\" shall mean Licensor and any individual or Legal Entity\n" +
                "      on behalf of whom a Contribution has been received by Licensor and\n" +
                "      subsequently incorporated within the Work.";
        licenses +="Retrofit";
        licenses +="Copyright 2013 Square, Inc.\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License.";

        licenses +="Glide";
        licenses +="Copyright 2012 Jake Wharton\n" +
                "Copyright 2011 The Android Open Source Project\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License.";

        return licenses;
    }
}