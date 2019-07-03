package jfxmaze;
class timer implements Runnable
{
    final int MIN_COUNT=10;
    int count;
    Thread countThread;
    boolean stop,pause;
    void start()
    {
        pause=false;
        stop=false;
        countThread=new Thread(this);
        countThread.start();
    }
    public void run()
    {
        while(stop!=true)
        {
            try{
                Thread.sleep(MIN_COUNT);
                if(pause!=true)
                    count+=MIN_COUNT;
            }catch(InterruptedException e){}
        }
    }
    void pause(){
        pause=true;
    }
    void cont(){
        pause=false;
    }
    void stop(){
        stop=true;
    }
    int getCount(){
        return count;
    }
}
