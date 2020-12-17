import json
import numpy as np
import pandas as pd 
import matplotlib.pyplot as plt 
import seaborn as sns 



from sklearn.cluster import KMeans

def parseLog():
  filename_in="/media/files/ProyectoDiseno/clustering/Patterns/prueba1.json"
  with open(filename_in, "r", encoding="utf8") as json_file:
    data = json.load(json_file)
  #print(data)
  
  f = open("/media/files/ProyectoDiseno/clustering/Patterns/prueba1.json", "r")
  content = f.read()
  jsondecoded = json.loads(content)
  
  #classNameData = []
  #packageData = []
  #modifiersAtributesData = []
  #modifiersMethodsData = []
  content_list = []
  for x in range(0,len(jsondecoded)):
    data1 = jsondecoded[x]
    string_content = ""
    string_content = string_content + "class name " + str(data1["className"] + " ")
    #classNameData.append(data1["className"]) #AST class name.
    string_content = string_content + "package name " + str(data1["package"] + " ")
    #packageData.append(data1["package"]) #AST package name.
    for entity in data1["atributes"]: 
      for entityModifier in entity["modifier"]:
        string_content = string_content + "modifier atribute " + str(entityModifier) + " "
        #modifiersAtributesData.append(entityModifier) # AST modifiers atributes type.
    for entity2 in data1["methods"]:
      for entityModifier2 in entity2["modifier"]:
        string_content = string_content + "modifier method " + str(entityModifier2) + " "
        #modifiersMethodsData.append(entityModifier2) # AST modifiers methods type.
    content_list.append(string_content)
  #print(classNameData)
  #print(packageData)
  #print(modifiersAtributesData)
  #print(modifiersMethodsData)
  print(len(content_list))
  news_df = pd.DataFrame()
  news_df.to_csv('data3.csv', index=False)


def prueba1():

  filename_in="/media/files/ProyectoDiseno/clustering/Patterns/prueba1.json"
  content = []
  with open(filename_in) as f:
      content = f.readlines()
  content = [json.loads(x.strip()) for x in content]
  print(content)
  """    
  data = json.loads(json.dumps(content))

  # preprocessing ////////////////////////////////
  content_list = []
  for i in data:
      string_content = ""
      if "contents" in i:
        for all in i["contents"]:
            if "content" in all:
              string_content = string_content + str(all["content"])
        content_list.append(string_content)
   
  df = pd.DataFrame(data)
  """


  #print(df)

  #df.to_csv('data.csv', index=False)


if __name__ == "__main__":
  parseLog()



