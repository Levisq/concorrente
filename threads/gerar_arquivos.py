import os

def gerar_arquivo(nome, tamanho):
    with open(nome, 'wb') as f:
        f.write(os.urandom(tamanho))  # escreve 'tamanho' bytes aleatórios

# Gera 3 arquivos com tamanhos diferentes
gerar_arquivo('arquivo1.bin', 100)   # 100 bytes
gerar_arquivo('arquivo2.bin', 250)   # 250 bytes
gerar_arquivo('arquivo3.bin', 500)   # 500 bytes
gerar_arquivo('arquivo4.bin', 100)   # 100 bytes
gerar_arquivo('arquivo5.bin', 250)   # 250 bytes
gerar_arquivo('arquivo6.bin', 500)   # 500 bytes
gerar_arquivo('arquivo7.bin', 100)   # 100 bytes
gerar_arquivo('arquivo8.bin', 250)   # 250 bytes
gerar_arquivo('arquivo9.bin', 500)   # 500 bytes

print("Arquivos gerados com sucesso!")