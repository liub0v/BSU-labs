#pragma once
#include <iostream>
#include <condition_variable>
#include <mutex>
#include <queue>

using namespace std;

#ifndef BUFFERED_CHANNEL_H_
#define BUFFERED_CHANNEL_H_

typedef unique_lock<mutex> mutex_lock;  

template <typename T>
class BufferedChannel
{
public:

	BufferedChannel(){}

	explicit BufferedChannel(int capacity) {
		buffer_capacity = capacity;
	}

	void Send(T item) 
	{
		mutex_lock lock(buffer_mutex);
		if (is_closed) 
		{
			throw runtime_error("Error!");
			return;
		}
		if (buffer.size() == buffer_capacity) 
		{
			send = false;
			while (!send) 
			{
				if (is_closed) break;
				sendcheck.wait(lock);
				
			}
		}
		
		buffer.push(item);
		recv = true;
		recvcheck.notify_one();
		
	}

	pair<T, bool> Recv() 
	{
		mutex_lock lock(buffer_mutex);

		if (is_closed && buffer.empty()) {
			return { T(), false };
		}
		if (buffer.empty()) {
			recv = false;
			while (!recv) {
				recvcheck.wait(lock);
				if (is_closed) break;
				if (buffer.empty()) recv = false;
			}
		}
		
		pair<T, bool> buff;
		buff = { buffer.front() , true };
		buffer.pop();
		send = true;
		sendcheck.notify_one();
		return buff;
	}

	void Close() {

		is_closed = true;
		sendcheck.notify_all();
		recvcheck.notify_all();
	}

private:

	queue<T> buffer;
	mutex buffer_mutex;
	int buffer_capacity;
	condition_variable sendcheck;
	condition_variable recvcheck;
	bool send = true;
	bool recv = false;
	bool is_closed = false;

};
#endif //BUFFERED_CHANNEL_H_