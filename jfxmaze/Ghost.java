package jfxmaze;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.application.*;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.event.*;
import javafx.scene.effect.*;
class Ghost extends solver
{
    boolean isChanging;
    Circle ghost;
    int temp,speed;
    double gRadius;
    Group root;
    Ghost(int so,int des,int pn,int adj[][],cel arr[][],Group r,int sp,double gR,Color gColor)
    {
        super(so,des,pn,adj,arr);
        gRadius=gR;
        root=r;
        speed=sp;
        isChanging=false;
        ghost=new Circle(grid[dest/n1][dest%n1].cx,grid[dest/n1][dest%n1].cy,gRadius);
        ghost.setFill(gColor);        
        Platform.runLater(new Runnable(){
			public void run()
			{
				root.getChildren().add(ghost);
			}
		});
    }
    int getCell()   //returns cell number of ghost
    {
        double x=ghost.getCenterX();
        double y=ghost.getCenterY();
        for(int i=0;i<n1;i++)
        {
            for(int j=0;j<n1;j++)
            {
                if(grid[i][j].cx==(x) && grid[i][j].cy==(y)/*grid[i][j].cx>(x-4) && grid[i][j].cx<(x+4) && grid[i][j].cy>(y-4) && grid[i][j].cy<(y+4)*/)
                    return grid[i][j].numb;
            }
        }
        System.out.println("return:"+n);
        return n;
    }
    void setNewSource(int fin){
        if(fin==-1)
            return;
        terminate=true;
        System.out.println("new route----------------------");
        while(isChanging==true){
            try{
                Thread.sleep(1);
            }catch(InterruptedException e){}
        }
        source=fin;
        dest=getCell();
    }
    void move()     //will move from DEST to SOURCE
    {
        bfs();
        System.out.println("new route");
        upadj();
    }
    void upadj()
	{
        isChanging=false;
		temp=dest;
		/*sl=grid[temp/n1][temp%n1];
		el=grid[parent[temp]/n1][parent[temp]%n1];
		if(ghost==null){
			ghost=new Circle(sl.cx,sl.cy,gRadius);
			ghost.setFill(Color.BLUE);
			Platform.runLater(new Runnable(){
				public void run()
				{
					root.getChildren().add(ghost);
				}
			});
		}*/
		terminate=false;
		while(temp!=source && terminate!=true)
		{
			/*adjmat[parent[temp]][temp]=2;
			adjmat[temp][parent[temp]]=2;*/
            try{
                sl=grid[temp/n1][temp%n1];
                el=grid[(parent[temp])/n1][(parent[temp])%n1];
            }catch(Exception e){
                System.out.println("temp:"+temp+" parent"+parent[temp]+" source:"+source);
            }
            isChanging=true;
			Platform.runLater(new Runnable(){
				public void run()
				{
					Timeline timeline = new Timeline();
					//timeline.setCycleCount(Timeline.INDEFINITE);
					KeyValue kvx = new KeyValue(ghost.centerXProperty(), el.cx);
					KeyValue kvy = new KeyValue(ghost.centerYProperty(), el.cy);
					KeyFrame kf = new KeyFrame(Duration.millis(speed), kvx,kvy);
					timeline.getKeyFrames().add(kf);
					timeline.play();
					/*ghost.setCenterX(el.cx);
					ghost.setCenterY(el.cy);*/
					//done=true;
					timeline.setOnFinished(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent e){
							isChanging=false;
						}
					});
				}
			});
			while(isChanging==true){
				try{
					Thread.sleep(2);
				}catch(InterruptedException e){}
			}
			/*try{
				Thread.sleep(200);
			}catch(InterruptedException e){}*/
			isChanging=false;
			temp=parent[temp];
		}
	}
}
