
### FileProvider

FileProvider 是ContentProvider的子类，它的功能是通过为某个文件创建content://Uri(代替以前使用的file:///Uri)，让app之间安全的共享该文件。

       想要读写content URI共享的文件，要申请一个临时读写权限（也就是说可以用临时读写权限，去访问content URI指定的文件），
  例如，你创建了Intent并且为该Intent设置了content URI，然后启动其他app，这时，你可以通过Intent.setFlag()方法添加权限，
  只要activity栈中的activity生命周期存活，这个权限会一直存在。用Intent启动service，只要service在运行，申请 的权限一直存在。
      与file:///Uri相比，file:///Uri访问文件必须改变文件系统底层文件的权限，
  并且你提供的权限可供任何app使用，在改变权限之前它会一直有效，这种访问机制在本质上不安全
  通过content URI提供的文件访问安全级别构成了Android基础安全的一部分
        

### 构建FileProvider
         
            FileProvider可以自动为文件生成content URI ，所以你不需要在代码中定义子类。
        相反，你可以通过xml的方式，让app包含一个FileProvider,。如何指      定FileProvider？
        在app的Manifest文件中添加<provider>元素。设置元素属性:
           1.android:name=android.support.v4.content.FileProvider(默认值，不能改)
           2.android:authorities=com.mydomain.fileprovider(设置URI,要唯一，通常用app包名，例如 pakgename+fileprovider)
           3.android:exprot = false(设置外部访问权限，官方文档上说，the FileProvider does not need to be public)
           4.android:grantUriPermissions=true(设置true，允许你申请一个临时访问文件权限)
         
         <manifest>
             ...
             <application>
                 ...
                 <provider
                     android:name="android.support.v4.content.FileProvider"
                     android:authorities="com.mydomain.fileprovider"
                     android:exported="false"
                     android:grantUriPermissions="true">
                     ...
                 </provider>
                 ...
             </application>
         </manifest>
    
        如果你想复写FileProvider函数的默认行为（复写函数），则要继承（expend FileProvider）并且使用xml文件中android:name指定的名称
        
        
 ### 指定共享文件
 
     FileProvider 只能为你预先指定的文件夹中的文件生成一个content URI。如何指定文件夹？
         指定文件夹：
              用xml文件指定文件夹的存储区域和path,使用<paths>元素中的子元素来指定。
              <paths xmlns:android="http://schemas.android.com/apk/res/android">
                 <files-path name="my_images" path="images/"/>
                 ...
              </paths>
         
        <paths>元素必须包含一个或多个下面的子元素```
           
            1. <files-path name="name" path="path"/>
             
              <files-path/>代表app内部存储区域，等价于Context.getFileDir("")(/data/data/packageName/files/)
              
           2. <cache-path name="name" path="path"/>
            
              <cache-file/>代表app内部存储区域，等价于Context.getFileCache("")(/data/data/packageName/caches/)
              
           3 <external-path name ="name" path="path"/>
              <external-path/> 代表app外部存储，根目录root等价于Enviroment.getExteranlStorageDirectory(“”)(一般是/storage/emulated/0)
               
           4.<external-files-path name="name" path="path" /> 
           <external-files-path/> 代表app外部存储，等价于Context.getExternalFilesDir(String)(/storage/emulated/0/Android/data/package/files/xx),
                 Context.getExternalFilesDir(null)(/storeage/emulated/0/Android/data/package/files).
                 
           5. <external-cache-path name="name" path="path" />
            
            <extreanl-cache-path/>代表app外部存储，等价于Context.getExternalCacheDir()(/storeage/emulated/0/Android/data/package/cache)
                
           6. <external-media-path name="name" path="path" />
               
               <external-media-path/>代表外部存储，等价于Context.getExternalMediaDirs(),该函数返回的文件夹必须在api21+的设备上
 
        通过上面可以看到，这些子元素使用了相同的属性，name=""和path=""
             name="name"
               URI的分割部分。这样加强了安全性，这样隐藏了你共享的文件的子目录的名称，子目录的名称通过path指定
             path="path"
                   你共享的子目录，name属性是URI的分割部分，path是真实的子目录。注意path指向的是子目录文件夹，不是一个有效的文件或多文件。
               你不能用path提供的文件名共享单一文件，也不能通过通配符共享一些文件
          
        你必须使用<paths>的子元素为每一个包含文件的文件夹指定一个你想要的content URIs。看下面的例子，xml元素指定了两个文件夹：
         
                <paths xmlns:android="http://schemas.android.com/apk/res/android">
                    <files-path name="my_images" path="images/"/>
                    <files-path name="my_docs" path="docs/"/>
                </paths>
           
      把<paths>元素和它的子元素放在你项目中的XML文件。例如，你可以把它们添加到你在新建的res/xml/file_paths.xml中，
      把这个文件连接到manifiel.xml文件中的FileProvider元素上。如何连接？
        为定义FileProvider的<provider>元素添加一个子元素<meta-data>。设置<meta-data>的android:name=android.support.FILE_PROVIDER_PATHS，android:resourse=@xml/file_paths(注意file_paths必须是.xml)。
        

        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="com.mydomain.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
        
        
        
 ### 为文件生成Content URI
 
          使用content URI和其他app共享文件，你的app必须生成一个Content URI，为共享的文件创建一个File,让后用getUriForFile()生成Content URI。
      你可以通过Intent发送getUriForFile()生成的Content URI，接收了Content URI 的app可以打开共享文件并读写它。如何读写？
          通过ContentResolver.openFileDescriptor()返回ParcelFileDescriptor。
          
       发送例子
       ```
       File imagePath = new File(Context.getFilesDir(), "images");
       File newFile = new File(imagePath, "default_image.jpg");
       Uri contentUri = getUriForFile(getContext(), "com.mydomain.fileprovider", newFile); 
       ```
      上面代码分析：
         通过getUriForFile()生成了一个content Uri(content://com.mydomain.fileprovider/my_images/default_image.jpg)
         
  
 ### 为URI申请一个临时权限
      为getUriForFile()返回的Uri申请permission,用下面方法中的一个就可以
          1.调用Context.grantUriPermission(package,Uri,mode_flags)为Uri申请权限，使用你自己期望的mode_flags为Uri申请权限，
          package参数会将uri的临时访问权限授予指定的包名。根据mode_flags参数给定的值，你可以设置FLAG_GRANT_READ_URI_PERMISSION,                         FLAG_GRANT_WRITE_URI_PERMISSION或者同时指定。申请的权限会一直保留，除非调用revokeUriPermission()撤销权限或者重启收起
          
         2. 用Intent的setData()函数，把Uri设置到Intent中，然后调用Intent.setFlags()指定FLAG_GRANT_READ_URI_PERMISSION or                               FLAG_GRANT_WRITE_URI_PERMISSION or both..最后发送Intent给另一个app。一般通过setResul()发送Intent。                                       
          用Intent申请的临时权限，当接收该intent的activity存活，则临时权限有效，反之，自动撤销权限。访问权限授权给某个app的一个activity后，
      该权限会自动扩展到这个app的其他组件。
       
       
#### 向另一个应用程序提供内容URI （Serving a Content URI to Another App）
         有多种方法可以将文件的内容URI提供给其他的app，一种通用的的方案是client app调用startActivityResult()启动你的app，
     startActivityResult()会携带Intent给你的app。
         你的app可以立刻返回一个Content URI 给client app或者提供允许用户选择文件的用户界面。在后一种情况，
      一旦用户选择了该文件，您的app就可以返回其内容URI ,
      
      在这两种情况下，您的应用都会返回通过setResult()发送的Intent中的内容URI
      
      You can also put the content URI in a ClipData object and then add the object to an Intent you send to a client app. To do this, call Intent.setClipData(). 
      When you use this approach, you can add multiple ClipData objects to the Intent, each with its own content URI. When you call Intent.setFlags() on the Intent 
      to set temporary access permissions, the same permissions are applied to all of the content URIs.
      
 ###  Android 系统的存储
      
      android系统提供了很多获得存储路径的方法，一般通过Enviroment和Context获取。
     
         1. 内部存储路径
            a. Enviroment.getDataDirctory() = /data ,内部存储的根目录（/data ）。
            b. 你的app的内部存储路径（如果没有root，看不到），/data/data/package/,没有找到获取方法。
            c. app缓存目录:Context.getCacheDir(null)(/data/data/package/cache),
               变种写法:Context.getCacheDir("info")(/data/data/package/cache/info/)
               
               app文件目录：Context.getFileDir(null)(/data/data/package/files),
               变种写法：Context.getFileDir("info")(/data/data/package/files/info/)
               
         2. 外部存储路径：
            a. Enviroment.getExteranlStorageDirectory() = /storage/emulated/0/(设备不同，路径可能不同)，外部存储根目录。
            b. app缓存目录：Context.getExternalCacheDir(null)(/storage/emulated/0/Android/data/package/cache),
                变种写法：Context.getExternalCacheDir("info");(/storage/emulated/0/Android/data/package/cache/info/)
                
               app文件目录:Context.getExternaleFileDir(null)(/storage/emulated/0/Android/data/packeage/file),
                变种写法：Context.getExteranlFileDir("info")(/storage/emulated/0/Android/data/package/file/info)
                Context.getExteranlFileDir(Enviroment.DIRECTORY_PICTURE)(/storage/emulated/0/Android/data/package/file/Pictures)
          
          3.公共存储：
             
                 我们可以在Android系统中的外部存储任意创建文件或者文件夹，但是在Android 6.0之后，要动态申请读写权限。
             创建后的文件夹或文件不会随着app的卸载而清除
             
             Enviroment.getExternalStorageDirectory()(/storage/emulated/0)
             Enviroment.getExternalStoragePublicDirectory("")(/storage/emulated/0)
             Enviroment.getExtrenalStoragePublicDirectory("info");(/storage/emulated/0/info/)
             Enviroment.getExternalStoragePublicDirectory(Environemt.DIRECTORY_PICTURES)(/storage/emulated/0/Pictures)
            


