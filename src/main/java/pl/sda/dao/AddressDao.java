package pl.sda.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.sda.entities.Address;
import pl.sda.entities.Client;
import pl.sda.entities.Employee;
import pl.sda.util.HibernateUtil;

import java.util.List;

public class AddressDao extends AbstractDao{
    public AddressDao() {
        super(Address.class);
    }}