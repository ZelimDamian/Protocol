directory.HomeView = Backbone.View.extend({

	el : '#homeContainer',
	
	initialize : function()
	{
		_.bindAll(this);
        this.searchResults = new directory.CustomerCollection();
        this.searchresultsView = new directory.CustomerListView({model: this.searchResults});
        this.customerSummaryView = new directory.CustomerSummaryView({collection:this.searchResults});
        this.searchResults.fetch();
	},

    events:{
        'click #saveCustomer': 'saveCustomer'
    },

    render:function () {
        this.$el.html(this.template());
        this.$el.find('.list-container').html(this.searchresultsView.render().el);
        this.$el.find('.customer-container').html(this.customerSummaryView.render().el);
        return this;
    },

    newCustomer:function(){
    	var customer = new directory.Customer();
    	this.customerSummaryView.setModel(customer);
    },
    
    renderCustomer:function(id){
    	var customer = this.searchResults.get(id);
    	if(customer)
    		this.customerSummaryView.setModel(customer);
    	else
    		this.newCustomer();
	},
    
	saveCustomer: function()
	{
		this.customerSummaryView.updateSaveModel();
	},
	
    showMeBtnClick:function () {
        directory.shellView.search();
    }

});