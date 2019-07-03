package jfxmaze;
class q
{
	qnode enq(qnode head,int enqdata)
	{
		qnode curr=head;
		if(head==null)
		{
			head=new qnode(enqdata);
		}
		else
		{
			while(curr.next!=null)
				curr=curr.next;
			curr.next=new qnode(enqdata);
			curr=curr.next;
		}
		return head;
	}
	qnode deq(qnode head)
	{
		return head.next;
	}
	int peek(qnode head)
	{
		return head.data;
	}
	boolean isEmpty(qnode head)
	{
		if(head==null)
			return true;
		return false;
	}
}
