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
  print(data)
  




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



