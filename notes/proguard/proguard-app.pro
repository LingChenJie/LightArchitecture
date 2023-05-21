# 输出文件记录控制，默认会在build/outputs/mapping/release生成
# apk 包内所有 class 的内部结构
-dump proguard/class_files.txt
# 未混淆的类和成员
-printseeds seeds.txt
# 列出从 apk 中删除的代码
-printusage unused.txt
# 混淆前后的映射
-printmapping mapping.txt

# 混淆保护自己项目的部分代码以及引用的第三方jar包
#-libraryjars xxx.jar