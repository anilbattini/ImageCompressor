# ImageCompressor
Compress image - 5MB to ~300KB

Just Keep this class in your project and call

###### `ImageCompressor.compressBitmap(file, bitmap)`

It even allows you to execute any callback after compressing the file.

```
###### ImageCompressor.compressBitmap(file, bitmap, { file ->
###### //Your logic using the updated compressed image file.
###### })
```

It maintains the same aspect ratio with relatively good quality.
Note: It only compresses JPG. 

If you are looking for just compress the image, this should do it.
But if you are looking for customized options to compress like quality, size then you take a look at [here](https://github.com/zetbaitsu/Compressor).

