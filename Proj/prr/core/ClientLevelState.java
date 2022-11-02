package prr.core;

/**
 * Padrão de Desenho - State
 */
public interface ClientLevelState{
    int calculateMessageCost(String message);
    int calculateVoiceCommCost(int duration);
    int calculateVideoCommCost(int duration);

    String toString();
}