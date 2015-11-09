package nl.hr.cmi.inf.citygis.model

import nl.hr.cmi.inf.citygis.external.Hashids;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id

/**
 * Created by cmi on 29-09-15.
 */
@Entity
class User {
    @Id
    @GeneratedValue
    Long id

    String username
    String firstName
    String lastName
    String email
    Date createdDate
    Date lastAccessed
    String hash;

    Boolean isActive = Boolean.TRUE


    def reCreateHash() {
        Hashids hashids = new Hashids()
        this.setHash(hashids.encode(this.id))
    }

    def setCurrentTimes() {
        def now = Calendar.instance
        this.setCreatedDate(now.time)
        this.setLastAccessed(now.time)
    }
}