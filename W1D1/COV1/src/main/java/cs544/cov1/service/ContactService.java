package cs544.cov1.service;

import cs544.cov1.dao.ContactDao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cs544.cov1.domain.Contact;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ContactService {

    @Resource
    private ContactDao contactDao;

    public Iterable<Contact> getContacts() {
        return contactDao.findAll();
    }

    public Contact getContact(long id) {
        return contactDao.findOne(id);
    }

    public void addContact(Contact contact) {
        contactDao.save(contact);
    }

    public void updateContact(Contact contact) {
        // manually merge the data
        Contact db = contactDao.findOne(contact.getId());
        db.setName(contact.getName());
        db.setEmails(contact.getEmails());
        db.setPhones(contact.getPhones());
    }

    public void deleteContact(Contact contact) {
        contactDao.delete(contact);
    }
}
