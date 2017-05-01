package view;

import model.User;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GuiGraphique implements Gui {

    // First GUI we need to keep it as data member to try to connect
    private GuiElement login;
    // Main Frame, we need to keep it to have a track of the interaction
    private Contacts mainWindows;
    // For a pseudo we keep the panelUserContact to be able to access it (to change status for example)
    private HashMap<String, PanelUserContact> userToPanel;
    // For a pseudo we keep the window of message
    private HashMap<String, Message> usertToChat;
    private GuiElement active;

    // for sound effect
    private SoundFX sound;

    public GuiGraphique() {
        userToPanel = new HashMap<>();
        usertToChat = new HashMap<>();

        sound  = new SoundFX();
    }

    public void launch() {
        login = GUIFactory.createGui(GUIFactory.TypeWindows.LOGIN);
        active = login;
    }

    public void setUserStatus(String name, String status) {
        userToPanel.get(name).setStatus();
        sound.playSound(status);

        switch(status){
            case "Online":
                this.setUserOnline(name);
                break;
            case "Offline":
                this.setUserOffline(name);
                break;
            default:
                System.out.println("user status inconnu");
                break;
        }

    }

    public void setUserOffline(String name){
        PanelUserContact panelUser = userToPanel.get(name);
        panelUser.disableTalk();
        panelUser.setStatus();
        mainWindows.setUserOffline(panelUser);
    }

    public void setUserOnline(String name){
        PanelUserContact panelUser = userToPanel.get(name);
        panelUser.enableTalk();
        panelUser.setStatus();
        mainWindows.setUserOnline(panelUser);
    }

    public void addUserToChat(String name, Message msg) {
            this.usertToChat.put(name, msg);
    }

    public void deliverMessage(network.Message msg) {
        usertToChat.get(msg.getSrcPseudo()).addMessage(msg.getData(), msg.getSrcPseudo());

        // change icon for new msg
        userToPanel.get(msg.getSrcPseudo()).alertMsg();
        sound.playSound("newMsg");
    }

    public void deliverText(String dest, String Message, String source) {
        usertToChat.get(dest).addMessage(Message, source);
    }

    public void addNewUser(User u1) {
        mainWindows.addNewUser(u1);
        sound.playSound("newUser");
    }

    /**
     * We add an panelUserContact withe corresponding username
     * @param name the name of the user
     * @param panel the panel on the contact window
     */
    public void addUserToPanel(String name, PanelUserContact panel) {
        this.userToPanel.put(name, panel);
    }

    public void createMainWindow() {
        sound.playSound("loginValid");

        try {
            TimeUnit.SECONDS.sleep(1);
            active.close();
            TimeUnit.SECONDS.sleep(1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mainWindows = (Contacts) GUIFactory.createGui(GUIFactory.TypeWindows.CONTACTS);
        active = mainWindows;
    }

    public void notif(String s) {
        active.notif(s);
    }

    @Override
    public void deliverImage(String dest, String path) {
        usertToChat.get(dest).addImage(path);
    }
}
