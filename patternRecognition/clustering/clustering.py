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


def parseLog(): 
  filename_in="/media/files/ProyectoDiseno/clustering/Patterns/prueba1.json"
  with open(filename_in, "r", encoding="utf8") as json_file:
    data = json.load(json_file)

  #First format
  news_df2 = pd.DataFrame({'document':data})
  news_df2.to_csv('data2.csv', index=False)
  
  #Atributes information
  for content in data:
    atributes = content.pop("atributes")
    content["atribute_modifiers"] = atributes[0]["modifier"]
    content["atribute_name"] = atributes[0]["name"]
    content["atribute_type"] = atributes[0]["type"]
  
  #Methods information
  for content1 in data:
    methods = content1.pop("methods")
    content1["methods_modifiers"] = methods[1]["modifier"]
    content1["methods_name"] = methods[1]["name"]
    content1["methods_arguments"] = methods[0]["arguments"]
    content1["methods_returnType"] = methods[1]["returnType"]
  
  #Second format
  news_df = pd.DataFrame(data)
  news_df.to_csv('data.csv', index=False)
  #print(len(d.columns))
  
  
  # tfidf vectorizer of scikit learn
  vectorizer = TfidfVectorizer(max_features=1000, max_df = 0.5, use_idf = True, ngram_range=(1,3))
  X = vectorizer.fit_transform(news_df)
  print(X.shape) # check shape of the document-term matrix
  terms = vectorizer.get_feature_names()

  num_clusters = 3
  km = KMeans(n_clusters=num_clusters)
  km.fit(X)
  clusters = km.labels_.tolist()

  # applying lsa //////////////////////////////

  U, Sigma, VT = randomized_svd(X, n_components=3, n_iter=100, random_state=122)
  #printing the concepts
  for i, comp in enumerate(VT):
        terms_comp = zip(terms, comp)
        sorted_terms = sorted(terms_comp, key= lambda x:x[1], reverse=True)[:7]
        print("Concept "+str(i)+": ")
        for t in sorted_terms:
            print(t[0])
        print(" ")
  
  X_topics=U*Sigma
  embedding = umap.UMAP(n_neighbors=3, min_dist=0.5, random_state=12).fit_transform(X_topics)
  plt.figure(figsize=(7,5))
  plt.scatter(embedding[:, 0], embedding[:, 1], c = clusters, s = 10, edgecolor='none') # s = size
  plt.show()
  
  print("done")


if __name__ == "__main__":
    parseLog()