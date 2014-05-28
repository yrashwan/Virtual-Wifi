dim prv, idx

ICSSC_DEFAULT         = 0
CONNECTION_PUBLIC     = 0
CONNECTION_PRIVATE    = 1
CONNECTION_ALL        = 2

set NetSharingManager = Wscript.CreateObject("HNetCfg.HNetShare.1")

idx = 0
set Connections = NetSharingManager.EnumEveryConnection
for each Item in Connections
	idx = idx + 1
	set Connection = NetSharingManager.INetSharingConfigurationForINetConnection(Item)
	set Props = NetSharingManager.NetConnectionProps(Item)
if Props.Name = "Wireless Network Connection 3" then Connection.EnableSharing CONNECTION_PRIVATE
if Props.Name = "Wireless Network Connection" then Connection.EnableSharing CONNECTION_PUBLIC
next
