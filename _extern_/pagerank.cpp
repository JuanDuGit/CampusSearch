#include <algorithm>
#include <cstdio>
#include <stdio.h>
#include <errno.h>

#include <string>
#include <string.h>

#include <vector>
#include <fstream>
#include <iostream>
#include <map>
using namespace std;

/*vector<float> PageRank(int N, int iter, float p){
	vector<float> rank(N), rank2(N);
	rank.assign(N,1.0/N);
	rank2.assign(N,0.0);
*/
  vector<int> outd, from, to, linkset, djs,ind;
  //vector<vector<int>> out;
  map<string, int> name2num;
  map<int, int> word_id2id;
  map<int, string> id2name;

	void readfile(char inNodeFile[], char inGraphFile[]){
		
		 ifstream fin(inNodeFile);
		 
     
	    string line; 
	    int word_id;
	    while( getline(fin,line) )
	    {    
	       // cout << "Read from file: " << line << endl;
	        char *pline = (char*)line.data(); 
	        char *p = strstr(pline,"-->");
	        if(p){
	        	*p = '\0';
	        	sscanf(p+3, "%d", &word_id);
	        	name2num[pline] = word_id;
	        	int id = (int)word_id2id.size();
	        	word_id2id[word_id] = id;
	        	id2name[id] = pline;
	        	
	        }
	    }
	    fin.close();
	    //cout << word_id2id[27093711]<<"		"<< word_id2id[]
	    int num = (int)word_id2id.size();
	    cout << num<<endl;

	    outd.assign(num,0);
	    from.assign(num,-1);
	    to.assign(num,-1);
	    ind.assign(num,0);
	    FILE *fin2 = fopen(inGraphFile,"r");
	   // ifstream fin2(); 
	    int node_id,id;
	    char c;
	    int g = 1;
	    while (fscanf(fin2,"%d%c",&node_id,&c) == 2){
	    	
	    	//cout << "ha"<<endl;
	    	if(c==':'){
	    		id = word_id2id[node_id];
	    		if(outd[id]) g = 0;
	    		else {
	    			g=1;
	    			from[id] = to[id]= linkset.size();
	    		}

	    	}else{
	    		if(g)
	    		{
		    		node_id =  word_id2id[node_id];
		    		linkset.push_back(node_id);
		    		to[id] = linkset.size();
		    		outd[id]+=1;
					ind[node_id]+=1;
				}
	    	}
	    }
	    fclose(fin2);

	}

	vector<float> pagerank(int N, int iter, float p){
		vector<float> rank(N),rank2(N);
		rank.assign(N, 1.0/N);
		rank2.assign(N, p/N);

		for(int k = 0; k < iter; k++){
			cout << k<<endl;
			float s = 1-p;
			fill_n(rank2.begin(),N,0.0);
			for(int i = 0; i<N; i++){
				if(from[i] == to[i]) 
					s += p*rank[i];
				else{
					float tmp = p*rank[i]/(to[i]-from[i]);
					for(int j = from[i];j<to[i]; j++){
						int v = linkset[j];
						rank2[v] += tmp;
					}
				}
			}
			for(int i = 0; i < N; i++){
				rank2[i] += s/N;
			}
			rank.swap(rank2);

		}
		return rank;
	}
	
	int main(int argc, char **argv){
		if (argc != 7)
		{
			cout << "usage: pagerank.exe inNodeFile inGraphFile outLinkScoreFile outNameScoreIndOutdFile tempIndFile tempOutdFile" << endl;
			return 0;
		}

		char *inNodeFile = argv[1];
		char *inGraphFile = argv[2];
		char *outLinkScoreFile = argv[3];
		char *outNameScoreIndOutdFile = argv[4];
		char *tempIndFile = argv[5];
		char *tempOutdFile = argv[6];
		
		ofstream SaveFile(outLinkScoreFile);
		ofstream saveres_name(outNameScoreIndOutdFile);		
		ofstream saveout(tempOutdFile);
		ofstream savein(tempIndFile); 
		readfile(inNodeFile, inGraphFile);
		int n = (int)word_id2id.size();
		int iter_num = 30;
		float p = 0.85;
				
		vector<float> rank = pagerank(n,iter_num,p);
		for(int i=0; i< n; i++){
			if((n%1000)==0) cout << n<<endl;
			saveres_name<<id2name[i]<<"	"<<rank[i]<<"	"<<ind[i]<<"	"<< to[i]-from[i]<<endl;
			saveout << to[i]-from[i]<<endl;
	    	savein << ind[i]<<endl;			
		}
		
		
		vector<int> index(n);
		for(int i = 0; i < n; i++) index[i] = i;
		sort(index.begin(),index.end(),[&](int x, int y){return rank[x]>rank[y];});

		for(int i = 0; i < n; i++){
			int num = index[i];
			SaveFile<<id2name[num].c_str()<<"	"<<rank[num]<<endl;
		}
		SaveFile.close();
		savein.close();
		saveout.close();
		saveres_name.close();
		return 0;
	}

