package lib.kalu.core.frame;

/**
 * description: 监听变化
 * created by kalu on 2018/4/10 11:47
 */
public abstract class OnModelChangeListener {

    public void modelStart(){
    }

    public void modelComplete(){
    }

    public void modelFail(){
    }

    public abstract void modelSucc();
}
