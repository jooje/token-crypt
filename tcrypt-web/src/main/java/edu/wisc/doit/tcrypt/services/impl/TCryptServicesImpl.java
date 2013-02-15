/**
 * @author Brad Leege <leege@doit.wisc.edu>
 * Created on 2/15/13 at 1:32 PM
 */
package edu.wisc.doit.tcrypt.services.impl;

import edu.wisc.doit.tcrypt.dao.IKeysKeeper;
import edu.wisc.doit.tcrypt.services.TCryptServices;
import edu.wisc.doit.tcrypt.vo.ServiceKey;
import java.security.KeyPair;
import java.util.Set;

public class TCryptServicesImpl implements TCryptServices
{
	private IKeysKeeper keysKeeper;

	/**
	 * Constructor
	 * @param keysKeeper DAO for working with Keys (Generating and File Storage)
	 */
	public TCryptServicesImpl(IKeysKeeper keysKeeper)
	{
		super();
		this.keysKeeper = keysKeeper;
	}

	@Override
	public Set<String> getListOfServiceNames()
	{
		return keysKeeper.getListOfServiceNames();
	}

	@Override
	public KeyPair generateKeyPair()
	{
		return keysKeeper.generateKeyPair();
	}

	@Override
	public Boolean writeServiceKeyToFileSystem(ServiceKey serviceKey)
	{
		return keysKeeper.writeServiceKeyToFileSystem(serviceKey);
	}

	@Override
	public ServiceKey readServiceKeyFromFileSystem(String serviceName)
	{
		return keysKeeper.readServiceKeyFromFileSystem(serviceName);
	}
}
