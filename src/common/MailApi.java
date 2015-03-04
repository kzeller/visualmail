package common;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class MailApi {

	private static final String PROPERTIES_PATH = new File(
			System.getProperty("user.dir")).getParent()
			+ File.separator + "credentials.properties";
	private String email;
	private String pw;
	private Store store;

	public MailApi() {
		parseCredentials();
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getInstance(props, null);
		try {
			store = session.getStore();
			store.connect("imap.gmail.com", email, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseCredentials() {
		Properties props = getCredentialsFile();
		if (props != null) {
			email = props.getProperty("email");
			pw = props.getProperty("pw");
		} else {
			System.err.println("Property File is missing");
		}
	}

	private Properties getCredentialsFile() {
		try {
			Properties props = new Properties();
			FileInputStream in = new FileInputStream(PROPERTIES_PATH);
			props.load(in);
			in.close();
			return props;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getMessagesCount() {
		Folder[] folderList = getFolderList();
		int count = 0;
		for (Folder folder : folderList) {
			try {
				count += folder.getMessageCount();
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}
		return count;
	}

	public Folder[] getFolderList() {
		Folder[] folders = new Folder[0];
		try {
			Folder defaultFolder = store.getDefaultFolder();
			return defaultFolder.list();
		} catch (Exception mex) {
//			mex.printStackTrace();
		}
		return folders;
	}
	
	public Message[] checkReadMails(String folderName) {
		Message[] messages = new Message[0];
		try {
			Folder folder = store.getFolder(folderName);
			folder.open(Folder.READ_ONLY);

			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), true);
			messages = folder.search(ft);
			return messages;

		} catch (Exception mex) {
			mex.printStackTrace();
		}
		return messages;
	}

	public Message[] checkUnreadMails(Folder folder) {
		Message[] messages = new Message[0];
		try {
			folder.open(Folder.READ_ONLY);

			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			messages = folder.search(ft);
			return messages;

		} catch (Exception mex) {
//			mex.printStackTrace();
		}
		return messages;
	}
	
	public int getUnreadMessagesCount() {
		Folder[] folderList = getFolderList();
		int count = 0;
		try {
			for (Folder folder : folderList) {
				Message[] checkUnreadMails = checkUnreadMails(folder);
				count += checkUnreadMails.length;
			}
		} catch (Exception e) {
		}
		return count;
	}

}
