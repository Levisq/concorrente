import os
import sys
import threading
import time

total_sum = 0
semaphore = threading.Semaphore(1)

def do_sum(path):
    local_sum = 0
    try:
        with open(path, 'rb', buffering=0) as f:
            byte = f.read(1)
            while byte:
                local_sum += int.from_bytes(byte, byteorder='big', signed=False)
                byte = f.read(1)
        print(f"{path} : {local_sum}")
        

        semaphore.acquire()
        global total_sum
        total_sum += local_sum
        semaphore.release()

    except Exception as e:
        print(f"Erro ao processar {path}: {e}")



# se for passar alguma coisa pra dentro da thread só colocar assim:
def thread_function(paths):
    for path in paths:
    #many error could be raised error. we don't care
        _sum = do_sum(path)
        print(path + " : " + str(_sum))

# thread funcition sem argumento pode ser feito porém neste caso escolhi usar o do_sum
def thread_function1():
    time.sleep(2)

if __name__ == "__main__":

    paths = sys.argv[1:]
    threads = [] # armazenar nossas threads :)
    threadSemArgumentos = threading.Thread(target=thread_function1) # é possivel criar threads sem argumentos 
    
    for path in paths: # basicamente aqui estou criando 1 thread para cada documento que vai ser lido
        t = threading.Thread(target=do_sum, args=(path,)) # cria a thread passando parametro e usamos o do_sum como thread function
        threads.append(t)
        print("sou a thread do " + path)
        t.start()

    #sempre fazer o join fora do start
    for t in threads:
        t.join()

    print(f"\nSoma total dos bytes: {total_sum}")
