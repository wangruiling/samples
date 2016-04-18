idea-multimarkdown 源码地址：https://github.com/vsch/idea-multimarkdown

在代码中最为关键的license包中，有源代码,通过修改代码破解了。

idea-multimarkdown.1.4.3下载:http://plugins.jetbrains.com/files/7896/23453/idea-multimarkdown.1.4.3.zip
LicenseAgent.class下载:http://download.csdn.net/detail/xiejx618/9416266
将这个文件覆盖C:\Users\用户\.IntelliJIdea15\config\plugins\idea-multimarkdown\lib\idea-multimarkdown.jar\com\vladsch\idea\multimarkdown\license\LicenseAgent.class,
这里的用户就是你计算机的用户名,如果是IJ找到对应的覆盖位置就可以了.

如果你想折腾,如何生成这个LicenseAgent.class,往下看.
打开com.vladsch.idea.multimarkdown.license.LicenseAgent来修改.下面是修改:

1.将com.vladsch.idea.multimarkdown.license.LicenseAgent#getLicenseExpires整个方法体改为return "Never Expires";
2.将com.vladsch.idea.multimarkdown.license.LicenseAgent#getLicenseCode最后一行return false;改为return true;
3.将com.vladsch.idea.multimarkdown.license.LicenseAgent#isValidLicense整个方法体改为return true;
4.将com.vladsch.idea.multimarkdown.license.LicenseAgent#isValidActivation整个方法体改为return true;
5.将com.vladsch.idea.multimarkdown.license.LicenseAgent#getLicenseType整个方法体改为 return "License";
6.将com.vladsch.idea.multimarkdown.license.LicenseAgent#getLicenseExpiringIn整个方法体改为 return 36000;
7.将com.vladsch.idea.multimarkdown.license.LicenseAgent#isActivationExpired整个方法体改为 return false;


修改完和编译LicenseAgent.java都没报错就可以得到LicenseAgent.class(这里将编译的Project language level设为低一点,比如6),最后使用winrar打开idea-multimarkdown.jar覆盖就可以了.

