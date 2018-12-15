# License
## key tool 生成私钥和公钥库
 
 ####生成密钥库和密钥
 
    keytool -genkey -alias privatekey -keysize 1024 -keystore D:\privateKey.store -validity 365
 
 ####导出密钥库内密钥的证书
    keytool -export -alias privatekey -keystore D:\privateKey.store -file D:\certfile.cer
 
 ####创建公钥库
    keytool -import -alias pulbiccert -file D:\certfile.cer -keystore D:\publicCerts.store
 
 ##解释
     -alias 表示密钥的别名
     
     -keystore 表示当前密钥属于哪个密钥库 
     
     -validity 表示有效期（单位：天）
## Server
