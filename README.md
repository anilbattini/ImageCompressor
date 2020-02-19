# ImageCompressor
Compress image - 5MB to ~250KB

Just Keep this class in your project and call
```
/**
 * This doesn't compress the original image file.
 * It compresses the bitmap and updates it to the new file and returns from app cache
 */
ImageCompressor.compressBitmap(context, originalImageFile)
```

It even allows you to execute any callback after compressing the file.
```
ImageCompressor.compressBitmap(context, originalImageFile, { file ->
   //Your logic using the updated compressed image file.
})
```

If you want to compress the given file directly. Use this

```
/**
 * This compress the original file.
 */
ImageCompressor.compressCurrentBitmapFile(originalImageFile)
```


It maintains the same aspect ratio with relatively good quality.
Note: It only compresses JPG. 
