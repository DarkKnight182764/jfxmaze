package jfxmaze;
import java.util.Random;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.collections.*;
import java.util.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.shape.*;
class prim
{
	cel grid[][];
	int n,vsize,fsize;
	cel visited[];
	cel frontier[];
	ArrayList<Node> ol;
	Random rand=new Random();
	Group root;
	cel rf;
	boolean done;
	prim(cel arr[][],int numb,Group r)
	{
		ol=new ArrayList<Node>();
		root=r;
		n=numb;
		grid=arr;
		visited=new cel[n*n];
		frontier=new cel[n*n];
	}
	void display(int arr[][],int n)
	{
		for(int a=0;a<n;a++)
		{
			for(int b=0;b<n;b++)
				System.out.print(arr[a][b]+",");
			System.out.println();
		}
		System.out.println("---------------");
	}
	void algo(Thread t,int adjmat[][])
	{
		int r=rand.nextInt(n*n);
		visited[0]=grid[r/n][r%n];
		vsize++;
		do
		{
			upfrontier(vsize-1);
			/*System.out.println("front:");
			display(frontier,fsize);*/
			r=rand.nextInt(fsize);
			//System.out.println("f:"+fsize+" v:"+vsize);
			rf=frontier[r];
			if(rf.i==rf.adj.i+1)
			{
				rf.adj.wbot=false;
				rf.wtop=false;
				adjmat[rf.adj.numb][rf.numb]=1;
				adjmat[rf.numb][rf.adj.numb]=1;
			}
			else if(rf.j==rf.adj.j-1)
			{
				rf.adj.wleft=false;
				rf.wright=false;
				adjmat[rf.adj.numb][rf.numb]=1;
				adjmat[rf.numb][rf.adj.numb]=1;
			}
			else if(rf.j==rf.adj.j+1)
			{
				rf.adj.wright=false;
				rf.wleft=false;
				adjmat[rf.adj.numb][rf.numb]=1;
				adjmat[rf.numb][rf.adj.numb]=1;
			}
			else if(rf.i==rf.adj.i-1)
			{
				rf.adj.wtop=false;
				rf.wbot=false;
				adjmat[rf.adj.numb][rf.numb]=1;
				adjmat[rf.numb][rf.adj.numb]=1;
			}
			visit(rf,r);
			// ObservableList<Node> ol=root.getChildren();
			if(rf.wtop==false)
				ol.add(rf.ltop);
				//g.strokeLine(tx,ty,tx+inc,ty);
			if(rf.wright==false)
				ol.add(rf.lright);
				//g.strokeLine(tx+inc,ty,tx+inc,ty+inc);
			if(rf.wbot==false)
				ol.add(rf.lbot);
				//g.strokeLine(tx+inc,ty+inc,tx,ty+inc);
			if(rf.wleft==false)
				ol.add(rf.lleft);
			if(rf.adj.wtop==false)
				ol.add(rf.adj.ltop);
				//g.strokeLine(tx,ty,tx+inc,ty);
			if(rf.adj.wright==false)
				ol.add(rf.adj.lright);
				//g.strokeLine(tx+inc,ty,tx+inc,ty+inc);
			if(rf.adj.wbot==false)
				ol.add(rf.adj.lbot);
				//g.strokeLine(tx+inc,ty+inc,tx,ty+inc);
			if(rf.adj.wleft==false)
				ol.add(rf.adj.lleft);
			if(vsize%15==0)
			{
				Platform.runLater(new Runnable(){					
					public void run()
					{
						for(Node temp:ol)
						{
							Line l=(Line)temp;
							final Timeline timeline = new Timeline();
			                //timeline.setCycleCount(Timeline.INDEFINITE);
			                final KeyValue kvx = new KeyValue(l.startXProperty(), l.getEndX());
			                final KeyValue kvy = new KeyValue(l.startYProperty(), l.getEndY());
			                final KeyFrame kf = new KeyFrame(Duration.millis(500), kvx,kvy);
			                timeline.getKeyFrames().add(kf);
			                timeline.play();
							//rootList.remove(temp);
						}
						ol.clear();
						done=true;
					}
				});
				while(done==false)
				{
					try{
						Thread.sleep(5);
					}catch(InterruptedException e){}
				}
				done=false;
			}
			try{
				Thread.sleep(0);
			}catch(InterruptedException e){}
			//display(adjmat,n*n);
			/*System.out.println("visited:");
			display(visited,vsize);*/
		}while(vsize!=n*n);
		//display(adjmat,n*n);
		try{
			Thread.sleep(100);
		}catch(InterruptedException e){}
			Platform.runLater(new Runnable(){
				public void run()
				{
					for(Node temp:ol)
					{
						Line l=(Line)temp;
						final Timeline timeline = new Timeline();
						//timeline.setCycleCount(Timeline.INDEFINITE);
						final KeyValue kvx = new KeyValue(l.startXProperty(), l.getEndX());
						final KeyValue kvy = new KeyValue(l.startYProperty(), l.getEndY());
						final KeyFrame kf = new KeyFrame(Duration.millis(500), kvx,kvy);
						timeline.getKeyFrames().add(kf);
						timeline.play();
						//rootList.remove(temp);
					}
				}
			});
		System.out.println("MAZE GENERATED!");
	}
	void visit(cel front,int r)
	{
		visited[vsize]=front;
		vsize++;
		frontier[r]=frontier[fsize-1];
		fsize--;
	}
	void upfrontier(int r)
	{
		int i=visited[r].i;
		int j=visited[r].j;
		cel temp;
		if(i-1>-1 && i-1<n)
		{
			temp=grid[i-1][j];
			if(validfront(temp))
			{
				frontier[fsize]=temp;
				fsize++;
				temp.adj=visited[r];
			}
		}
		if(i+1>-1 && i+1<n)
		{
			temp=grid[i+1][j];
			if(validfront(temp))
			{
				frontier[fsize]=temp;
				fsize++;
				temp.adj=visited[r];
			}
		}
		if(j-1>-1 && j-1<n)
		{
			temp=grid[i][j-1];
			if(validfront(temp))
			{
				frontier[fsize]=temp;
				fsize++;
				temp.adj=visited[r];
			}
		}
		if(j+1>-1 && j+1<n)
		{
			temp=grid[i][j+1];
			if(validfront(temp))
			{
				frontier[fsize]=temp;
				fsize++;
				temp.adj=visited[r];
			}
		}
	}
	boolean validfront(cel front)
	{
		for(int i=0;i<vsize;i++)
		{
			if(visited[i]==front)
				return false;
		}
		for(int i=0;i<fsize;i++)
		{
			if(frontier[i]==front)
				return false;
		}
		return true;
	}
}
