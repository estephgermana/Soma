export interface notificacaopac {
    titulo: string;
    remetente: string;
    corpo: string;
    documentUrl?: string;
  }
  const notificacaopaciente: notificacaopac[] =[
    {
      titulo: "Mecanismos Moleculares e Genéticos no Alzheimer",
      remetente: "De: Dr. Rafael",
      corpo: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eget ligula eu lectus lobortis condimentum. Aliquam nonummy auctor massa. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla at risus. Quisque purus magna, auctor et, sagittis ac, posuere eu, lectus. Nam mattis, felis ut adipiscing.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eget ligula eu lectus lobortis condimentum. Aliquam nonummy auctor massa. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla at risus. Quisque purus magna, auctor et, sagittis ac, posuere eu, lectus. Nam mattis, felis ut adipiscing.",
      documentUrl: "https://link-para-documento.com",
    }
]
export default notificacaopaciente
