import java.util.Random;

public class Mountain {
	public static int board[][]=new int[11][13];
	public static int dice[]=new int[4];//四つの出目を格納
	public static int diceCal[]=new int[6];
	public static int diceCal2[]=new int[6];
	public static int selectNum[][]=new int[6][2];//ユーザーが選択肢の何番を選んだかの中身
	public static int proceedMass[]=new int[11];//スタートから何マス目にいるか
	public static int team=0;//ボード上のチーム数
	public static int firstGoal=-1;//初めにゴールしたライン名
	public static int goalTeam=0;//ゴールしたチーム数
	public static int teamPosition[]=new int[11];//チームの居場所,０はいない1はいる
	public static int startPosition[]=new int[11];
	public static int time=0;//何時間たったか。
	public static int numOfMountain;

	public static int item[]=new int [22];//持ってきてるアイテム
	
	/*public static void main(String[] args){
		makeBoard();
		reTeam();
		while(goalTeam!=2){
			System.out.println("team"+team);
			System.out.println("firstGoal"+firstGoal);
			System.out.println("goalTeam"+goalTeam);
			System.out.println("proceedMass");
			for(int k=0;k<11;k++){
				System.out.print(proceedMass[k]);
			}
			System.out.println();
			System.out.println("teamPosition");
			for(int k=0;k<11;k++){
				System.out.print(teamPosition[k]);
			}
			System.out.println();
			System.out.println("startPosition");
			for(int k=0;k<11;k++){
				System.out.print(startPosition[k]);
			}
			System.out.println();
			drawBoard();
			dice=dice();
			diceCal=diceCal(dice);
			System.out.println("出目は");
			for(int i=0;i<4;i++){
				System.out.println(dice[i]);
			}
			System.out.println("です。");
			System.out.println("組を選んでください。(1,2,3...のいずれか)");
			System.out.println("休憩は9を入力して下さい。");
			drawSelect(diceCal);
			for(int i=0;i<6;i++){
				System.out.println(diceCal[i]);
			}
			System.out.println("区切り");
			for(int i=0;i<6;i++){
				System.out.println(diceCal2[i]);
			}
			drawSelect2(diceCal2);
			Scanner scan = new Scanner(System.in);
			String str = scan.next();
			int a = Integer.parseInt(str);
			if(a==9){
				rest();
			}
			else{
				proceedFrame(a);
			}
		}
	}*/
	public void setNumOfMountain(int a){
		numOfMountain=a;
	}
	public int getNumOfMountain(){
		return numOfMountain;
	}
	public void increaseTime(){
		time++;
	}
	public int getTime(){
		return time;
	}
	public  int[][] getBoard(){
		return board;
	}
	public int[] getDice(){
		return dice;
	}
	public int[][] getSelectNum(){
		return selectNum;
	}
	//イベントが起こるかどうか乱数で決定する
	public int eventNum(){
		 Random rnd = new Random();


		 if(numOfMountain==1){
			 int ran = rnd.nextInt(10);
			 if(ran==1){
				 int a=rnd.nextInt(5);//5個の通常イベント
				 return a+11;
			 }
			 return -1;
		 }
		 else if(numOfMountain==2){
			 int ran = rnd.nextInt(10);
			 if(ran==1){
				 int a=rnd.nextInt(8);//8個の通常+冬山イベント
				 return a+11;
			 }
			 return -1;
		 }
		 else{
			 int ran = rnd.nextInt(10);
			 if(ran==1){
				 int a=rnd.nextInt(9);//9個のイベント
				 return a+11;
			 }
			 return -1;
		 }
	}
	//イベント起こす
	public String getOverLog(int a){
		if(a==13){
			return "クマに襲われました。ゲームオーバー";
		}
		if(a==16){
			return "凍傷にかかりました。ゲームオーバー";
		}
		return "登頂しました！";
	}
	public void event(int a){
		if(a==11){
			time=time+2;
		}
		if(a==12){
			time=time+2;
		}
		if(a==13){
			//クマに襲われたとき
		}
		if(a==14){

		}
		if(a==15){
			time=time+2;
		}
		if(a==16){
			//凍傷になった時
		}
		if(a==17){
			time=time+3;
		}
		if(a==18){
			time=time+4;
		}
		if(a==19){
			time=time+6;
		}
	}
	public  void rest(){
		team=0;
		for(int i=0;i<11;i++){

			if(teamPosition[i]==1){
				for(int j=0;j<13;j++){
					if(board[i][j]==5){
						board[i][j]=0;
					}
				}
				startPosition[i]=proceedMass[i];
				if(board[i][proceedMass[i]]!=3){
					board[i][proceedMass[i]]=5;
				}
			}
			proceedMass[i]=-1;
			teamPosition[i]=0;
		}
	}
	//時間を経過させる
	public void  addTime(){
		time=time+3;
	}

	//バースト時の盤面やらの処理
	public void burst(){
		team=0;

		for(int i=0;i<11;i++){
			proceedMass[i]=-1;
			teamPosition[i]=0;
		}
		for(int i=0;i<11;i++){
			for(int k=0;k<13;k++){
				board[i][k]=0;//0は何もない状態
			}
		}
		for(int i=0;i<6;i++){
			int k=(2*(i+1));
			board[i][k]=2;//2はゴール地点
			board[10-i][k]=2;
			for(int l=k+1;l<13;l++){
				board[i][l]=4;
				board[10-i][l]=4;//4は何もないとこ
			}
		}
		//スタート地点の設定,5はスタート地点
		for(int i=0;i<11;i++){
			if(startPosition[i]!=-1){
				board[i][startPosition[i]]=5;
			}
		}
	}
	public  void proceedFrame(int a){//コマを進める
		a=a-1;
		int i=0;
		int a1=-1;

		while(i<2){
			if(selectNum[a][i]!=-1){
				a1=selectNum[a][i]-2;//何列目か
				if(teamPosition[a1]==1){						//既存登山チームが上るとき
					if(board[a1][proceedMass[a1]+1]==2){//進んだらゴールの時
						team--;
						goalTeam++;
						for(int h=0;h<13;h++){
							board[a1][h]=3;
						}
						firstGoal=a1;
						proceedMass[a1]++;
					}
					else{
						board[a1][proceedMass[a1]]=0;
						board[a1][proceedMass[a1]+1]=1;
						proceedMass[a1]++;
					}
				}
				else{//既存チームがいないとき
					if(startPosition[a1]!=-1){//休んでるとき
						if(board[a1][startPosition[a1]+1]==2){//進んだらゴールの時
							team--;
							goalTeam++;
							for(int h=0;h<13;h++){
								board[a1][h]=3;
							}
							firstGoal=a1;
							proceedMass[a1]++;
						}
						else{
							team++;
							teamPosition[a1]=1;
							board[a1][startPosition[a1]+1]=1;
							proceedMass[a1]=startPosition[a1]+1;
						}
					}
					else{
						team++;
						teamPosition[a1]=1;
						proceedMass[a1]=0;
						board[a1][0]=1;
					}
				}
			}
			i++;
		}
	}
	public  void reTeam(){
		goalTeam=0;
		team=0;
		firstGoal=-1;
		for(int i=0;i<11;i++){
			teamPosition[i]=0;
			proceedMass[i]=-1;
			startPosition[i]=-1;//i番目のスタート地は0マス目
		}
	}
	public  void drawSelect2(int diceCal2[]){
		int select=0;
		for(int i=0;i<6;i++){
			selectNum[i][0]=-1;
			selectNum[i][1]=-1;
		}
		int n=0;
		while(n<6){
			if(diceCal2[n]==-1&&diceCal2[n+1]==-1){
				n=n+2;
			}
			else if(diceCal2[n]>=2&&diceCal2[n+1]==-1){
				System.out.println(1+select+":"+diceCal2[n]);
				selectNum[select][0]=diceCal2[n];
				select++;
				n=n+2;
			}
			else if(diceCal2[n]>=2&&diceCal2[n+1]>=2&&diceCal[n]!=diceCal[n+1]){
				System.out.print(1+select+":"+diceCal2[n]);
				System.out.println(" "+diceCal2[n+1]);
				selectNum[select][0]=diceCal2[n];
				selectNum[select][1]=diceCal2[n+1];
				select++;
				n=n+2;
			}
			else if(diceCal2[n]<-1&&diceCal2[n+1]<-1){
				int a=diceCal2[n]*(-1);
				int b=diceCal2[n+1]*(-1);
				System.out.print(1+select+":"+a);
				selectNum[select][0]=a;
				select++;
				System.out.print(" ");
				System.out.println(1+select+":"+b);
				selectNum[select][0]=b;
				select++;
				n=n+2;
			}
			else if(diceCal2[n]>=2&&diceCal2[n+1]>=2&&diceCal[n]==diceCal[n+1]){
				System.out.println(1+select+":"+diceCal[n]);
				selectNum[select][0]=diceCal[n];
				selectNum[select][1]=diceCal[n+1];
				select++;
				n=n+2;
			}
		}
	}

	public  void drawSelect(int diceCal[]){//選択肢を選別
		for(int i=0;i<6;i++){
			diceCal2[i]=-1;
		}
		//同じ数字の時３３、一つの数字受理は３－１、どっちかの時はー２－３どっちもは２３
		if(goalTeam==0){
			if(team==0||team==1){
				if(diceCal[0]!=-1){
					diceCal2[0]=diceCal[0];
					diceCal2[1]=diceCal[1];
				}
				if(diceCal[2]!=-1){
					diceCal2[2]=diceCal[2];
					diceCal2[3]=diceCal[3];
				}
				if(diceCal[4]!=-1){
					diceCal2[4]=diceCal[4];
					diceCal2[5]=diceCal[5];
				}
			}
			if(team==2){
				int n=0;
				while(n<6){
					if(diceCal[n]!=-1){
						if(diceCal[n]==diceCal[n+1]){
							diceCal2[n]=diceCal[n];
							diceCal2[n+1]=diceCal[n+1];
						}
						else{
							int k=0;
							for(int j=0;j<2;j++){
								for(int i=0;i<11;i++){
									if(teamPosition[i]==1||firstGoal-2==i){
										if((i+2)==diceCal[j+n]){
											k++;
										}
									}
								}
							}
							if(k==0){
								diceCal2[n]=diceCal[n]*(-1);
								diceCal2[n+1]=diceCal[n+1]*(-1);
							}
							else{
								diceCal2[n]=diceCal[n];
								diceCal2[n+1]=diceCal[n+1];
							}
						}
						n=n+2;
					}
					else{
						n=n+2;
					}
				}
			}
			if(team==3){
				int n=0;
				while(n<6){
					if(diceCal[n]!=-1){
						if(diceCal[n]==diceCal[n+1]){
							for(int i=0;i<11;i++){
								if(teamPosition[i]==1||firstGoal-2==i){
									if((i+2)==diceCal[n]){
										diceCal2[n]=diceCal[n];
										diceCal2[n+1]=diceCal[n+1];
									}
									else{
										diceCal2[n]=-1;
										diceCal2[n+1]=-1;
									}
								}
							}
						}
						else{
							int k=0;
							int num=-1;//被った数字をいれる
							for(int j=0;j<2;j++){
								for(int i=0;i<11;i++){
									if(teamPosition[i]==1){
										if((i+2)==diceCal[n+j]){
											k++;
											num=diceCal[n+j];
										}
									}
								}
							}
							if(k==0){
								diceCal2[n]=-1;
								diceCal2[n+1]=-1;
							}
							if(k==1){
								diceCal2[n]=num;
								diceCal2[n+1]=-1;
							}
							if(k==2){
								diceCal2[n]=diceCal[n];
								diceCal2[n+1]=diceCal[n+1];
							}
						}

					}
					n=n+2;
				}
			}
		}
		else if(goalTeam==1){
			if(team==0){
				int n=0;
				while(n<6){
					if(diceCal[n]!=-1){
						if(diceCal[n]==diceCal[n+1]){
							if(firstGoal+2==diceCal[n]){
								diceCal2[n]=-1;
								diceCal2[n+1]=-1;
							}
							else{
								diceCal2[n]=diceCal[n];
								diceCal2[n+1]=diceCal[n+1];
							}
						}
						else{
							if(firstGoal+2==diceCal[n]){
								diceCal2[n]=diceCal[n+1];
								diceCal2[n+1]=-1;
							}
							else if(firstGoal+2==diceCal[n+1]){
								diceCal2[n]=diceCal[n];
								diceCal2[n+1]=-1;
							}
							else{
								diceCal2[n]=diceCal[n]*(-1);
								diceCal2[n+1]=diceCal[n+1]*(-1);
							}
						}
					}
					n=n+2;
				}
			}
			if(team==1){
				int n=0;
				while(n<6){
					if(diceCal[n]!=-1){
						if(diceCal[n]==diceCal[n+1]){
							if(firstGoal+2==diceCal[n]){
								diceCal2[n]=-1;
								diceCal2[n+1]=-1;
							}
							else{
								diceCal2[n]=diceCal[n];
								diceCal2[n+1]=diceCal[n+1];
							}
						}

						else{
							int k=0;
							int num=-1;
							for(int j=0;j<2;j++){
								for(int i=0;i<11;i++){
									if(teamPosition[i]==1){
										if((i+2)==diceCal[n+j]){
											k++;
											num=diceCal[n+j];
										}
									}
								}
							}
							if(k==0){
								if(diceCal[n]==firstGoal+2){
									diceCal2[n]=diceCal[n+1];
									diceCal[n+1]=-1;
								}
								else if(diceCal[n+1]==firstGoal+2){
									diceCal2[n]=diceCal[n];
									diceCal[n+1]=-1;
								}
								else{
									diceCal2[n]=diceCal[n]*(-1);
									diceCal2[n+1]=diceCal[n+1]*(-1);
								}
							}
							if(k==1){
								if(diceCal[n]==firstGoal+2){
									diceCal2[n]=diceCal[n+1];
									diceCal2[n+1]=-1;
								}
								else if(diceCal[n+1]==firstGoal+2){
									diceCal2[n]=diceCal[n];
									diceCal2[n+1]=-1;
								}
								else{
									diceCal2[n]=diceCal[n];
									diceCal2[n+1]=diceCal[n+1];
								}
							}
						}
					}
					n=n+2;
				}
			}
			if(team==2){
				int n=0;
				int num1=0,num2=0;//ちーむがいるとこ
				for(int l=0;l<11;l++){
					if(teamPosition[l]==1){
						if(num1==0){
							num1=l+2;//ちーむがあるところ
						}
						else{
							num2=l+2;
						}
					}
				}
				while(n<6){
					if(diceCal[n]!=-1){//同じ数出たとき
						if(diceCal[n]==diceCal[n+1]){
							if(diceCal[n]==num1||diceCal[n]==num2){
								diceCal2[n]=diceCal[n];
								diceCal2[n+1]=diceCal[n+1];
							}
							else{
								diceCal2[n]=-1;
								diceCal2[n+1]=-1;
							}
						}
						else{
							int k=0;
							int num11=-1,num12=-1;
							for(int j=0;j<2;j++){
								if(num1==diceCal[n+j]){
									k++;
									if(num11==-1){
										num11=diceCal[n+j];
									}
									else{
										num12=diceCal[n+j];
									}
								}
								if(num2==diceCal[n+j]){
									k++;
									if(num11==-1){
										num11=diceCal[n+j];
									}
									else{
										num12=diceCal[n+j];
									}
								}
							}
							if(k==0){
								diceCal2[n]=-1;
								diceCal2[n+1]=-1;
							}
							if(k==1){
								diceCal2[n]=num11;
								diceCal2[n+1]=-1;
							}
							if(k==2){
								diceCal2[n]=diceCal[n];
								diceCal2[n+1]=diceCal[n+1];
							}
						}
					}
					n=n+2;
				}
			}
		}
	}
	public void incGoalTeam(){
		++goalTeam;
	}
	public void playMiharukasu(){

		if(item[17]==1){

		}
	}
	public void showItem(){
		for(int i=0;i<22;i++){
			System.out.println(this.item[i]);
		}
	}

	public void setItem(int[] a){
		this.item=a;
	}
	public int[] getItem(){
		return item;
	}
	public void drawBoard(){//ボードの表示
		for(int k=12;k>=0;k--){
			System.out.println();
			for(int i=0;i<11;i++){
				System.out.print(board[i][k]);
			}
		}
		System.out.println();
		System.out.print("23456789012");


		System.out.println();
	}
	public int[][] makeBoard(){//初期ボードの作成
		for(int i=0;i<11;i++){
			teamPosition[i]=0;
		}
		for(int i=0;i<11;i++){
			for(int k=0;k<13;k++){
				board[i][k]=0;//0は何もない状態
			}
		}
		for(int i=0;i<11;i++){
			board[i][0]=0;//スタート地点も0
		}
		for(int i=0;i<6;i++){
			int k=(2*(i+1));
			board[i][k]=2;//2はゴール地点
			board[10-i][k]=2;
			for(int l=k+1;l<13;l++){
				board[i][l]=4;
				board[10-i][l]=4;
			}
		}
		return board;
	}
	public void dice(){//さいころを四つふる
		 Random rnd = new Random();
		 int ran1 = rnd.nextInt(6)+1;
		 int ran2 = rnd.nextInt(6)+1;
		 int ran3 = rnd.nextInt(6)+1;
		 int ran4 = rnd.nextInt(6)+1;

		 dice[0]=ran1;
		 dice[1]=ran2;
		 dice[2]=ran3;
		 dice[3]=ran4;

	}
	public int checkFinish(){
		if(goalTeam==2){
			return 1;
		}
		else{
			return 0;
		}
	}
	public int[] diceCal(int dice[]){//さいころの加算結果を返す
		System.out.println("team"+team);
		System.out.println("firstGoal"+firstGoal);
		System.out.println("goalTeam"+goalTeam);
		System.out.println("proceedMass");
		for(int k=0;k<11;k++){
			System.out.print(proceedMass[k]);
		}
		System.out.println();
		System.out.println("teamPosition");
		for(int k=0;k<11;k++){
			System.out.print(teamPosition[k]);
		}
		System.out.println();
		System.out.println("startPosition");
		for(int k=0;k<11;k++){
			System.out.print(startPosition[k]);
		}

		int diceCal[]=new int[6];
		int a1=dice[0]+dice[1];
		int a2=dice[2]+dice[3];
		int b1=dice[0]+dice[2];
		int b2=dice[1]+dice[3];
		int c1=dice[0]+dice[3];
		int c2=dice[1]+dice[2];
		diceCal[0]=a1;
		diceCal[1]=a2;
		int i=2;
		if((a1==b1&&a2==b2)||(a1==b2&&a2==b1)){
			diceCal[4]=-1;
			diceCal[5]=-1;
		}
		else{
			diceCal[2]=b1;
			diceCal[3]=b2;
			i=4;
		}
		if(((a1==c1&&a2==c2)||(a1==c2&&a2==c1))||((b1==c1&&b2==c2)||(b1==c2&&b2==c1))){
			diceCal[i]=-1;
			diceCal[i+1]=-1;
		}
		else{
			diceCal[i]=c1;
			diceCal[i+1]=c2;
		}
		return diceCal;
	}


}


