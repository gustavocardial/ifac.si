import { Post } from './post';
import { Imagem } from './imagem';

export class ImagemHandler {
  // Prepara o arquivo para envio
  static async prepararImagem(file: File, post: Post): Promise<any> {
    // Cria um objeto com os dados do arquivo
    const imagemData = {
      nomeArquivo: file.name,
      tamanho: file.size,
      url: null,
      dataUpload: null,
      post: post  // O arquivo em si que será enviado
    };

    return imagemData;
  }
}