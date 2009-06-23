Name:           db2tm
Version:        0.0.1
Release:        damico%{?dist}
Summary:        DB2 multiple connection tester

Group:          System Environment/Base
License:        gpl
BuildRoot:      %{_tmppath}/%{name}-%{version}
BuildArch:      noarch
Requires:       filesystem
Requires:	java >= 1.5

%description
DB2 multiple connection tester.
 Can operate with up to 15 servers
 For more information see http://db2tm.googlecode.com/

%prep


%install

%post
chmod  +x /usr/bin/db2tm

%clean
rm -rf $RPM_BUILD_ROOT


%files
/usr/bin/db2tm
/usr/lib/db2tm/**
/etc/db2tm/**