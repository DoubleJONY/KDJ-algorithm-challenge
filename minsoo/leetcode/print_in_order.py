# https://leetcode.com/problems/print-in-order/

from threading import Condition


class Foo:
    """
    Have all three threads attempt to acquire an RLock via Condition.
    https://leetcode.com/problems/print-in-order/solutions/335939/5-python-threading-solutions-barrier-lock-event-semaphore-condition-with-explanation/

    The first thread can always acquire a lock, while the other two have to wait for the order to be set to the right value.
    First thread sets the order after printing which signals for the second thread to run. Second thread does the same for the third.
    """

    def __init__(self):
        self.condition = Condition()
        self.cnt = 0

    def first(self, printFirst: 'Callable[[], None]') -> None:
        with self.condition:
            # printFirst() outputs "first". Do not change or remove this line.
            printFirst()
            self.cnt += 1

            self.condition.notify_all()

    def second(self, printSecond: 'Callable[[], None]') -> None:
        with self.condition:
            self.condition.wait_for(lambda: self.cnt == 1)

            # printSecond() outputs "second". Do not change or remove this line.
            printSecond()
            self.cnt += 1

            self.condition.notify()

    def third(self, printThird: 'Callable[[], None]') -> None:
        with self.condition:
            self.condition.wait_for(lambda: self.cnt == 2)

            # printThird() outputs "third". Do not change or remove this line.
            printThird()
