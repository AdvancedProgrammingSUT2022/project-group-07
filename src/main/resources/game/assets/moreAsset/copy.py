import shutil

numbers = [277,276,258,243,237,229,224,204,202,201,199,139,136,135,127,109,103,100,93,92,91,32] 

src = "D:/Sharif/Daneshgah stuff/AP/AP-Project/project-group-07/src/main/resources/game/assets/moreAsset/"
dst = "D:/Sharif/Daneshgah stuff/AP/AP-Project/project-group-07/src/main/resources/game/images/icons" 

for x in numbers :
    
    num = str(x)
    if len(num)==1 :
        num = '00' + num 
    if len(num)==2 :
        num = '0' + num   

    shutil.copy2( src + num + ".png", dst)

