package jfxmaze;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import java.util.*;
import javafx.collections.*;
import javafx.scene.transform.*;
import javafx.scene.input.*;
public class fx3 extends Application implements Runnable
{
    timer time1;
    int k,ii,time1c;
    Scene pScene;
    double pStartX,pStartY,pRadius,offset;
    boolean lw,rw,tw,bw,MGstart;
    int i,j;
    int side,adjmat[][],end,show;
    static int n;
	cel cell[][];
	double inc;
	Thread tCreate;
    Group root;
    double tx,ty;
    cel temp;
    Ghost g[];
	//solver solve;
	public void init()
	{
        g=new Ghost[n*n];
        offset=100;
        MGstart=false;
		show=0;
		side=950;
		cell=new cel[n][n];
		adjmat=new int[n*n][n*n];
		inc=side/n;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				cell[i][j]=new cel(j*inc+offset,i*inc+offset,i,j,inc,n);
			}
		}
        System.out.println("init done");
		cell[0][0].wtop=false;
		cell[n-1][n-1].wbot=false;
		//solve=new solver(0,n*n-1,n,t,this);
    }
    @Override
	public void run()
	{
		prim obj=new prim(cell,n,root);
		obj.algo(tCreate,adjmat);
		end=1;
		/*solve.setadj(adjmat);
		solve.setgrid(cell);
		solve.bfs();*/
	}
    void begin()
    {
        tCreate=new Thread(this);
        tCreate.start();
    }
    public static void main(String[] args)
    {
        n=Integer.parseInt(args[0]);
        launch(args);
    }
    void solve()
    {
        Random r=new Random();
        Thread tt1=new Thread(new Runnable(){
            public void run()
            {
                for(ii=0;ii<1;ii++)
                {
                    Thread tt=new Thread(new Runnable(){
                        public void run()
                        {
                            g[ii]=new Ghost(ii,n*n-1-ii,n,adjmat,cell,root,100,pRadius,new Color(r.nextDouble(),r.nextDouble(),r.nextDouble(),1));
                            g[ii].move();
                        }
                    });
                    tt.start();
                    try{
                        Thread.sleep(50);
                    }catch(InterruptedException e){}
                }
            }
        });
        tt1.start();
    }
    public void start(Stage pStage)
    {
        pStartX=cell[0][0].cx;
        pStartY=cell[0][0].cy;
        pRadius=inc/5;
        root=new Group();
        pScene=new Scene(root,1080,1080,Color.LIGHTYELLOW);
        pStage.setScene(pScene);
        VBox hb=new VBox();
        Button b=new Button("Start");
        b.setMinWidth(1000);
        b.setStyle("-fx-background-color:lightblue;");
        Button bSolve=new Button("Solve");
        bSolve.setMinWidth(1000);
        bSolve.setStyle("-fx-background-color:tomato;");
        Button bPlay=new Button("Play");
        bPlay.setMinWidth(1000);
        bPlay.setStyle("-fx-background-color:#fa8100;");
        Button bTerminate=new Button("terminate");
        hb.getChildren().addAll(b,bSolve,bPlay,bTerminate);
        ObservableList<Node> tempOL=root.getChildren();
        tempOL.add(hb);
        b.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e)
            {
                begin();
                MGstart=true;
            }
        });
        bSolve.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e)
            {
                solve();
            }
        });
        EventHandler<MouseEvent> clickPlay=new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me)
            {
                if(tCreate!=null && tCreate.isAlive()==false && MGstart==true)
                {
                    time1=new timer();
                    time1c=time1.getCount();
                    time1.start();
                    solve();
                    play();
                }
            }
        };
        k=1;
        bPlay.addEventHandler(MouseEvent.MOUSE_RELEASED,clickPlay);
        bTerminate.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent ae)
            {
                Thread tt1=new Thread(new Runnable(){
                    public void run()
                    {
                        for(ii=0;ii<1;ii++)
                        {
                            Thread tt=new Thread(new Runnable(){
                                public void run()
                                {
                                    g[ii].setNewSource(n*n-1-ii);
                                    g[ii].move();
                                }
                            });
                            tt.start();
                            try{
                                Thread.sleep(50);
                            }catch(InterruptedException e){}
                        }
                    }
                });
                tt1.start();
            }
        });
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                temp=cell[i][j];
                tx=temp.x;
                ty=temp.y;
                if(temp.wtop)
                    tempOL.add(temp.ltop);
                if(temp.wright)
                    tempOL.add(temp.lright);
                if(temp.wbot)
                    tempOL.add(temp.lbot);
                if(temp.wleft)
                    tempOL.add(temp.lleft);
            }
        }
        pStage.show();
    }
    void play()
    {
        ObservableList<Node> tempOL=root.getChildren();
        System.out.println("Entering play mode");
        Group p=new Group();
        Circle c=new Circle(pStartX,pStartY,pRadius);
        //Line li=new Line(300,300,500,300);
        p.getChildren().addAll(c);
        c.setFill(Color.RED);
        tempOL.add(p);
        Translate t=new Translate();
        p.getTransforms().add(t);
        EventHandler<KeyEvent> move=new EventHandler<KeyEvent>(){
            public void handle(KeyEvent k)
            {
                Line testl=null;
                if(k.getText().equals("d"))
                {
                    System.out.println("d pressed");
                    t.setX(t.getX()+inc/2);
                    for(Node n:tempOL)
                    {
                        if(n instanceof Line)
                            testl=(Line)n;
                        if(isCollision(c,testl,t))
                        {
                            t.setX(t.getX()-inc/2);
                        }
                    }
                }
                if(k.getText().equals("a"))
                {
                    System.out.println("a pressed");
                    t.setX(t.getX()-inc/2);
                    for(Node n:tempOL)
                    {
                        testl=null;
                        if(n instanceof Line)
                            testl=(Line)n;
                        if(isCollision(c,testl,t))
                        {
                            t.setX(t.getX()+inc/2);
                        }
                    }
                }
                if(k.getText().equals("w"))
                {
                    System.out.println("w pressed");
                    t.setY(t.getY()-inc/2);
                    for(Node n:tempOL)
                    {
                        if(n instanceof Line)
                            testl=(Line)n;
                        if(isCollision(c,testl,t))
                        {
                            t.setY(t.getY()+inc/2);
                        }
                    }
                }
                if(k.getText().equals("s"))
                {
                    System.out.println("s pressed");
                    t.setY(t.getY()+inc/2);
                    for(Node n:tempOL)
                    {
                        if(n instanceof Line)
                            testl=(Line)n;
                        if(isCollision(c,testl,t))
                        {
                            t.setY(t.getY()-inc/2);
                        }
                    }
                }
                if(time1.getCount()%50-time1c>50){
                    Thread tc=new Thread(new Runnable(){
                        public void run()
                        {
                            int celln=getCell(c.getCenterX()+t.getX(),c.getCenterY()+t.getY());
                            g[0].setNewSource(celln);
                            if(celln!=-1)
                                g[0].move();
                        }
                    });
                    tc.start();
                    time1c=time1.getCount();
                }
            }
        };
        pScene.addEventHandler(KeyEvent.KEY_PRESSED,move);
    }
    int getCell(double x,double y)   //returns cell number of ghost
    {
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(cell[i][j].cx==(x) && cell[i][j].cy==(y)/*cell[i][j].cx>(x-4) && cell[i][j].cx<(x+4) && cell[i][j].cy>(y-4) && cell[i][j].cy<(y+4)*/)
                    return cell[i][j].numb;
            }
        }
        return -1;
    }
    boolean isCollision(Circle c,Line l,Translate t)
    {
        if(l==null)
        {
            System.out.println("False");
            return false;
        }
        Line orthoL1=getOrtho(l,l.getStartX(),l.getStartY());
        Line orthoL2=getOrtho(l,l.getEndX(),l.getEndY());
        double len=getLength(l);
        double radius=c.getRadius();
        if(getDistancecl(c,orthoL1,t)>len+radius || getDistancecl(c,orthoL2,t)>len+radius)
            return false;
        else
        {
            if(getDistancecl(c,l,t)<radius)
                return true;
            else
                return false;
        }
    }
    Line getOrtho(Line l,double a,double b)
    {
        double x1=l.getStartX(),y1=l.getStartY(),x2=l.getEndX(),y2=l.getEndY(),m,mr,ca=1,cr;
        Line res;
        if(x2==x1)
        {
            res=new Line(a,b,0,b);
            return res;
        }
        if(y2==y1)
        {
            res=new Line(a,b,a,0);
            return res;
        }
        m=(y2-y1)/(x2-x1);
        mr=-1/m;
        cr=b-mr*a;
        res=new Line(a,b,0,cr);
        res.setStroke(Color.RED);
        return res;
    }
    double getLength(Line l)
    {
        double x1=l.getStartX(),y1=l.getStartY(),x2=l.getEndX(),y2=l.getEndY();
        return (Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)));
    }
    double getDistance(Line l,double cx,double cy)
    {
        double a=1,m;
        double x1=l.getStartX(),y1=l.getStartY(),x2=l.getEndX(),y2=l.getEndY();
        if(x1==x2)
        {
            return (Math.abs(cx-x1));
        }
        m=-1*(y2-y1)/(x2-x1);
        double c=-1*(y1+m*x1);
        double dist=(Math.abs(cx*m+cy*a+c))/(Math.sqrt(m*m+a*a));
        return dist;
    }
    double getDistancecl(Circle c,Line l,Translate t)
    {
        return (getDistance(l,c.getCenterX()+t.getX(),c.getCenterY()+t.getY()));
    }
    public void stop()
    {
        System.out.println("End");
    }
}
