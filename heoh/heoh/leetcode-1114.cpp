#include <semaphore.h>


class Foo {
public:
    sem_t first_semaphore;
    sem_t second_semaphore;
    sem_t third_semaphore;

    Foo() {
        sem_init(&second_semaphore, 0, 0);
        sem_init(&third_semaphore, 0, 0);
    }

    void first(function<void()> printFirst) {
        // printFirst() outputs "first". Do not change or remove this line.
        printFirst();

        sem_post(&second_semaphore);
    }

    void second(function<void()> printSecond) {
        sem_wait(&second_semaphore);

        // printSecond() outputs "second". Do not change or remove this line.
        printSecond();

        sem_post(&third_semaphore);
    }

    void third(function<void()> printThird) {
        sem_wait(&third_semaphore);

        // printThird() outputs "third". Do not change or remove this line.
        printThird();
    }
};
