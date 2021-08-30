from multiprocessing import Process, Queue
import argparse

def work(id, start, end, result):
    total = 0
    for i in range(start, end):
        total += i
    result.put(total)
    return

parse = argparse.ArgumentParser(description='Lotto Number gathering')
parse.add_argument('--target', choices=['number', 'statistics'], required=True, type=str, help='수집 내용')
parse.add_argument('--select', choices=['range', 'one', 'latest'], required=False, type=str, help='범위 수집, 한 회 수집')

if __name__ == "__main__":
    START, END = 0, 100000000
    result = Queue()
    th1 = Process(target=work, args=(1, START, END // 2, result))
    th2 = Process(target=work, args=(2, END // 2, END, result))

    th1.start()
    th2.start()
    th1.join()
    th2.join()

    result.put('STOP')
    total = 0
    while True:
        tmp = result.get()
        if tmp == 'STOP':
            break
        else:
            total += tmp
    print(f"Result: {total}")