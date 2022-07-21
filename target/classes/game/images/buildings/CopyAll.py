import os
import shutil

src = r"C:/Users/Mahan/Desktop/Buildings/"
dst = r"D:/Sharif/Daneshgah stuff/AP/AP-Project/project-group-07/src/main/resources/game/images/buildings/"

# fetch all files
for file_name in os.listdir(src):
    # construct full file path
    source = src + file_name
    destination = dst + file_name.upper().replace(".PNG", ".png")
    # copy only files
    if os.path.isfile(source):
        shutil.copy(source, destination)