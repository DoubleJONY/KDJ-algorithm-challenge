using System.Threading;

public class Foo {

    private readonly AutoResetEvent secondARE = new AutoResetEvent(false);
    private readonly AutoResetEvent thirdARE = new AutoResetEvent(false);

    public Foo() {
    
    }

    public void First(Action printFirst) {
        
        // printFirst() outputs "first". Do not change or remove this line.
        printFirst();
        secondARE.Set();
    }

    public void Second(Action printSecond) {
        
        // printSecond() outputs "second". Do not change or remove this line.
        secondARE.WaitOne();
        printSecond();
        thirdARE.Set();
    }

    public void Third(Action printThird) {
        
        // printThird() outputs "third". Do not change or remove this line.
        thirdARE.WaitOne();
        printThird();
    }
}