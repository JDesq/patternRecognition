import json
import numpy as np
import pandas as pd 
import matplotlib.pyplot as plt 

from sklearn.cluster import KMeans

def main():

  filename_in="/media/files/ProyectoDiseno/clustering/Patterns/singleton.json"

  with open(filename_in, "r", encoding="utf8") as json_file:
    data = json.load(json_file)


  for gPackage in data:
    nameSpace = gPackage.pop("gPackage")
    gPackage["nameSpace"] = nameSpace["nameSpace"]
  #for nameSpace in data: 
  #  packageName = nameSpace.pop("nameSpace")
  #  nameSpace["nameSpace"] = packageName["nameSpace"]
  # nameSpace["packageName"] = packageName["nameString"] #Name of the package
  
  for opensScope in data:
    declOrDefn = opensScope.pop("opensScope")
    opensScope["declOrDefn"] = declOrDefn["declOrDefn"]
   
  df = pd.DataFrame(data)

  """dataset = ["/media/files/ProyectoDiseno/clustering/Patterns/singleton.json"]
  data = []
  for file in dataset:
    with open(file, "r") as f:
      for line in f.readlines():
        data.append(json.loads(line))
  data_df(data)

def data_df(data):
  df = pd.DataFrame()

  #df["@class"] = list(map(lambda dataF: dataF["class"],data))
  df["opensScope"] = list(map(lambda dataF: dataF["opensScope"],data))
  #df["imports"] = list(map(lambda dataF: dataF["imports"],data))"""

 


  print(df)

  df.to_csv('data.csv', index=False)


if __name__ == "__main__":
  main()



