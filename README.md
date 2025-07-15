# All About This Project

This is a project that was done as a take-home assignment for a Coro job application. It receives
messages, scans them for credit-card-number-like strings, and stores a timestamped count of them.
You can also get a report on detections over time intervals.

You can run this project via:

```bash
> mvn package
> java -jar target/CoroAssignment-0.0.1-SNAPSHOT.jar
```

Also, I've been using Intellij and you can open it there, and it should figure out how to run it.
Have a nice time!

## Performance and Scaling

The expected load given in the assignment is 300 messages per second. On my (admittedly nice)
laptop, it already handles 2,500 messages in under one second, so that's fine. It is worth noting
that, in this case, the requesting process, the JVM and the database are sitting on the same
machine. This may speed things up because of high proximity, or it may slow things down because
they're all sharing resources.

In terms of further scaling, we have to consider two bottlenecks:

1. The Java application itself
2. The MongoDb database

### The Java Application Itself

In this case, the program can be scaled up without difficulty. We are not overwriting or changing
existing data, and no request depends on another (although the result can depend on the order in
which requests get processed.) Therefore, there's no problem scaling up the number of instances to
handle the load, and putting them behind a load balancer.

### The MongoDb Database
The folks at Mongo have figured out how to scale the database via usual replication, sharding, etc.
You can replicate the database and reads will go to the replicas, but only one copy can be written
to. If performance problems are due to high read volumes, this could help us. If it's due to high
write volumes, we'd have to explore other options.
Sharding is one possibility. Based on the kind of queries we're doing on the database right now, it
seems like ranged sharding, which would distribute the data into different shards based on the sent
time.

### Expected Performance Issues

One performance issue I foresee is with the "detections" endpoint. It returns information about all
the credit card number detections that have occurred in a period. That can result in scanning and
returning a large amount of data. It may be wise to limit the range for these requests, or to limit
the number of returned results to some amount, like 100 detections. 

### Other Possible Optimizations

#### Caching

It's unclear that caching would help this particular system. Caching helps when you get many of the
same request repeatedly. If that's the case here, great, but I don't see a reason to expect it. My
code makes requests randomly, although there's no reason to think it behaves like real-world people.
The other problem with caching is that exactly the same request may return a different result if
more data has come in. The user seems to provide the sent time, which means any result could change
at any time. If sent times always go up (which seems reasonable), then perhaps queries regarding
past times can be cached. And maybe we expect certain queries (say, from the start of one month to
the start of the next) to be particularly in-demand. and caching those may be reasonable.

#### A Message Broker

A message broker is useful when processed need to communicate with each other, but not necessarily
synchronously.
This is a very small system, so message broker doesn't have much of a role to play here. The
/detections endpoint must return data synchronously (although data export services from some
companies allow you to request data and let you know later when it's ready. We could do that.)

Regarding the /message endpoint, we could use a message queue. Instead of writing straight to the
database, we could write to Kafka (or whichever message broker), and have a consumer pick it up and
write it to that database. That would free threads in the first jvm to handle http requests if it's
faster to write to Kafka. If there's some reason we only have one machine which is web-facing, but
other machines available to do database writes, this may be useful. On the other hand, this is a
strange scenario, and it increases the complexity of the system (which is easy, since this system
starts out quite simple) and may not be worth it, depending on actual constraints.