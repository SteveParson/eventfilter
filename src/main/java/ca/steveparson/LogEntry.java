package ca.steveparson;

public class LogEntry {

	public long		max_hole_size,
					packets_serviced,
					packets_requested,
					retries_request,
					request_time;

	public String	client_guid = "",
					client_address = "",
					service_guid = "";		

	public LogEntry(	long max_hole_size,
						long packets_serviced,
						long packets_requested,
						long request_time,
						long retries_request,
						String client_guid,
						String client_address,
						String service_guid) {
		
		this.max_hole_size = max_hole_size;
		this.packets_serviced = packets_serviced;
		this.packets_requested = packets_requested;
		this.request_time = request_time;
		this.retries_request = retries_request;
		this.client_guid = client_guid;
		this.client_address = client_address;
		this.service_guid = service_guid;
	}
}
