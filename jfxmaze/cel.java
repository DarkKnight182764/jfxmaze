package jfxmaze;
import javafx.scene.shape.*;
class cel
{
	double x,y;
	int i,j,numb;
	double inc,cx,cy;
	boolean wleft,wright,wtop,wbot;
	Line ltop,lright,lbot,lleft;
	cel adj;
	cel(double x,double y,int i,int j,double inc,int n)
	{
		this.inc=inc;
		this.i=i;
		this.j=j;
		this.x=x;
		this.y=y;
		wleft=wright=wtop=wbot=true;
		cx=x+inc/2;
		cy=y+inc/2;
		numb=n*i+j;
		ltop=new Line(x,y,x+inc,y);
		lright=new Line(x+inc,y,x+inc,y+inc);
		lbot=new Line(x+inc,y+inc,x,y+inc);
		lleft=new Line(x,y+inc,x,y);
	}
}
