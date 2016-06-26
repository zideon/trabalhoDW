/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.trabalhodw.persistence;

import br.uff.trabalhodw.model.Ticket;

/**
 *
 * @author fabio
 */
public class TicketDAO  extends AbstractJpaDao<Ticket,Long> {

    public TicketDAO() {
        super(Ticket.class);
    }
    
}
