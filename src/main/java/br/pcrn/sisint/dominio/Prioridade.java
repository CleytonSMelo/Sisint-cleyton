package br.pcrn.sisint.dominio;

import br.pcrn.sisint.conversor.ConvertivelOpcaoSelect;

public enum Prioridade implements ConvertivelOpcaoSelect{
    ALTA("Alta"), MEDIA("Média"), BAIXA("Baixa"), AGUARDANDO_ANALISE("Em Análise");

    private String chave;

    Prioridade(String chave) {
        this.chave = chave;
    }

    @Override
    public String getChave() {
        return chave;
    }

    @Override
    public String getValor() {
        return this.toString();
    }
}
