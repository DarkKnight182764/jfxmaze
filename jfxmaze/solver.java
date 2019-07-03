package jfxmaze;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.event.*;
class solver
{
	int adjmat[][],n,source,dest,visited[],parent[],vctr,n1;
	cel grid[][],sl,el;
	boolean done,terminate;
	solver(int s,int d,int pn,int adj[][],cel g[][])
	{
		terminate=false;
		this.n=pn*pn;
		n1=pn;
		source=s;
		dest=d;
		visited=new int[n];
		parent=new int[n];
		adjmat=adj;
		grid=g;
	}
	boolean bvisited(int v)
	{
		for(int i=0;i<vctr;i++)
		{
			if(visited[i]==v)
				return true;
		}
		return false;
	}
	void bfs()
	{
		System.out.println("source:"+source+" dest:"+dest);
		int v,u;
		q queue=new q();
		vctr=0;
		visited[vctr]=source;
		vctr++;
		parent[source]=-1;
		qnode head=queue.enq(null,source);
		while(queue.isEmpty(head)==false)
		{
			//displayq(head);
			u=queue.peek(head);
			//System.out.println("u:"+u);
			head=queue.deq(head);
			//System.out.println("head:"+head);
			for(int i=0;i<n;i++)
			{
				if(adjmat[u][i]==1)
				{
					v=i;
					if(bvisited(v)==false)
					{
						visited[vctr]=v;
						vctr++;
						parent[v]=u;
						head=queue.enq(head,v);
						//displayq(head);
					}
				}
			}
		}
		//display();
	}
	void display()
	{
		//System.out.println(psize);
		for(int i=0;i<n;i++)
		{
			System.out.println("parent of "+i+" is "+parent[i]);
		}
	}
	void displayadj()
	{
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				System.out.print(adjmat[i][j]);
			}
			System.out.println();
		}
	}
	void displayq(qnode head)
	{
		System.out.println("queue:");
		qnode curr=head;
		while(curr!=null)
		{
			System.out.println(curr.data);
			curr=curr.next;
		}
	}
}
