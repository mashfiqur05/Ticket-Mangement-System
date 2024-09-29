package Interfaces;
import Classes.*;

public interface ITicketSellerList {
    
    public abstract void addNewAgent (Agent agent);
    public Agent[] getSellerList();
    public int isUserExist(String name);
}
