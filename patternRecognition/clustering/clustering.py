import json
import sys
import numpy as np
import pandas as pd 
import matplotlib.pyplot as plt 
import seaborn as sns 
import nltk 
import umap

from nltk.corpus import stopwords

from sklearn.cluster import KMeans
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.decomposition import TruncatedSVD
from sklearn.utils.extmath import randomized_svd 


def clustering(): 
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
  #print(content_list)
  news_df = pd.DataFrame(columns=content_list)
  news_df.to_csv('data3.csv', index=False)

  
  # tfidf vectorizer of scikit learn
  vectorizer = TfidfVectorizer(max_features=100, max_df = 0.5 ,use_idf = True, ngram_range=(1,3))
  X = vectorizer.fit_transform(news_df)
  print(X.shape) # check shape of the document-term matrix
  terms = vectorizer.get_feature_names()

  num_clusters = 2
  km = KMeans(n_clusters=num_clusters)
  km.fit(X)
  clusters = km.labels_.tolist()

  # applying lsa //////////////////////////////

  U, Sigma, VT = randomized_svd(X, n_components=10, n_iter=100, random_state=122)
  #printing the concepts
  for i, comp in enumerate(VT):
        terms_comp = zip(terms, comp)
        sorted_terms = sorted(terms_comp, key= lambda x:x[1], reverse=True)[:7]
        print("Concept "+str(i)+": ")
        for t in sorted_terms:
            print(t[0])
        print(" ")
  
  X_topics=U*Sigma
  embedding = umap.UMAP(n_neighbors=50, min_dist=0.5, random_state=12).fit_transform(X_topics)
  plt.figure(figsize=(7,5))
  plt.scatter(embedding[:, 0], embedding[:, 1], c = clusters, s = 20, edgecolor='none') # s = size
  plt.show()
  
  print("done")


if __name__ == "__main__":
    clustering()