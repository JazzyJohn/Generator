package nstuff.generator.entity;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class MapPoint {
    private int x;

    private int y;

    private int height=-1;

    public MapPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

   public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if(height<0){
            this.height =0;
        }else {
            this.height = height;
        }
    }
    public void setStartHeight(int height) {
        if(this.height ==-1){
            this.height = height;
        }
    }
    @Override
    public String toString() {

        return "x:"+x+" y:"+y+" height:"+height;
    }
}
