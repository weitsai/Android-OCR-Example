Android-OCR-Example
===================

練習使用 [tess-two](https://github.com/rmtheis/tess-two) 來辨識圖片的文字，因為本人的開發環境是 OS X, 如果是其他作業系統平台的可能要參考一下官方的說明文件喲!!

# 環境需求
* [Android SDK](http://developer.android.com/sdk/index.html)
* [Android NDK](https://developer.android.com/tools/sdk/ndk/index.html) -  因為 [tess-two](https://github.com/rmtheis/tess-two) 套件是使用 c 撰寫, 所以必須先透過 NDK 編譯 jni 下面的檔案
* [ANT](http://ant.apache.org/)
* [homebrew](http://brew.sh/) - 作業系統是 OS X 的人應該都有安裝這套來管理套件吧！！ 其他平台可以忽略

# 開發環境
* OS X: 10.9.5
* Shell: zsh
* IDE: Eclipse
* Android Version: 4.4.2

# 環境設定
## Install NDK
1. 先到官方下載 [NDK](https://developer.android.com/tools/sdk/ndk/index.html) 並解壓縮
2. 開啓 bashrc or zshrc 將剛剛解壓縮出來的檔案路徑設定到 export
```
  export ANDROID_NDK="[NDK_PATH]"
  export PATH="$PATH:$ANDROID_SDK/platform-tools:$ANDROID_NDK"
```

## Install ANT
1. brew install ant
> 其他平台的人可能需要[官方](http://ant.apache.org/)的安裝教學

## Build Tess-two
```
  1. git clone git://github.com/rmtheis/tess-two tess
  2. cd tess/tess-two
  3. ndk-build
  4. android update project --path .
  5. ant release
```

## Import Tess-two Libary
1. 先把 tess 中的 tess-two 專案 import 到 eclipse, 並設定他為 Libary Project, 如果有問題請看[官方教學](http://developer.android.com/tools/projects/projects-eclipse.html#SettingUpLibraryProject)
2. 接著我們的專案就可以 Import tess-two Libary 囉, 如果不知道怎麼使用請參考[官方教學](http://developer.android.com/tools/projects/projects-eclipse.html#ReferencingLibraryProject)

## Setting traineddata file
1. 下載 traineddata 檔 ([下載點](https://code.google.com/p/tesseract-ocr/downloads/list)) - 如果只是要辨識英文就下載 eng, 若是中文則下載 chi.
2. 透過 adb push 把檔案放到 SDCard -  要注意 tesseract/tessdata/ 這個路徑是固定的喔！！
```
  adb push ~/Downloads/tesseract-ocr/tessdata/chi_tra.traineddata /mnt/sdcard/tesseract/tessdata/.
```


# API 使用方式
## 判斷 Bitmap 中的文字
1. 產生 bitmap 圖片
```
  private static Bitmap getTextImage(String text, int width, int height) {
    final Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    final Paint paint = new Paint();
    final Canvas canvas = new Canvas(bmp);
    
    canvas.drawColor(Color.WHITE);
    
    paint.setColor(Color.BLACK);
    paint.setAntiAlias(true);
    paint.setTextAlign(Align.CENTER);
    paint.setTextSize(24.0f);
    canvas.drawText(text, width / 2, height / 2, paint);
    
    return bmp;
  }
```

2. 辨識 bitmap 中的文字
```
  TessBaseAPI baseApi = new TessBaseAPI();
  baseApi.init("/mnt/sdcard/tesseract/", "chi_tra");
  baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
  baseApi.setImage(getTextImage("你好, hello", 300, 300));
  System.out.println(baseApi.getUTF8Text());
```

執行結果
=============
![alt tag](http://api.drp.io/files/542f0aa0583f6.png)


參考資料來源
=============
1. https://github.com/rmtheis/tess-two
2. https://code.google.com/p/tesseract-ocr/downloads/list
3. http://www.androidadb.com/source/tesseract-android-tools-read-only/tesseract-android-tools-test/src/com/googlecode/tesseract/android/test/TessBaseAPITest.java.html
